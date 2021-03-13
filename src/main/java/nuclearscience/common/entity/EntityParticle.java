package nuclearscience.common.entity;

import java.util.HashSet;

import electrodynamics.api.math.Location;
import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import nuclearscience.DeferredRegisters;
import nuclearscience.api.fusion.IElectromagnet;
import nuclearscience.common.block.BlockElectromagneticBooster;
import nuclearscience.common.block.facing.FacingDirection;
import nuclearscience.common.tile.TileElectromagneticSwitch;
import nuclearscience.common.tile.TileParticleInjector;

public class EntityParticle extends Entity {
    private static final DataParameter<Direction> DIRECTION = EntityDataManager.createKey(EntityParticle.class,
	    DataSerializers.DIRECTION);
    private static final DataParameter<Float> SPEED = EntityDataManager.createKey(EntityParticle.class,
	    DataSerializers.FLOAT);
    private Direction direction;
    public float speed = 0.02f;
    public BlockPos source = BlockPos.ZERO;

    public EntityParticle(EntityType<?> entityTypeIn, World worldIn) {
	super(entityTypeIn, worldIn);
    }

    public EntityParticle(Direction direction, World worldIn, Location pos) {
	this(DeferredRegisters.ENTITY_PARTICLE.get(), worldIn);
	forceSetPosition(pos.x(), pos.y(), pos.z());
	this.direction = direction;
	ignoreFrustumCheck = true;
	if (worldIn.isRemote) {
	    setRenderDistanceWeight(4);
	}
    }

    @Override
    public void tick() {
	TileEntity tile = world.getTileEntity(source);
	if (!world.isRemote) {
	    if (!(tile instanceof TileParticleInjector)) {
		remove();
		return;
	    }
	    ((TileParticleInjector) tile).addParticle(this);
	    dataManager.set(DIRECTION, direction);
	    dataManager.set(SPEED, speed);
	} else {
	    direction = dataManager.get(DIRECTION);
	    speed = dataManager.get(SPEED);
	}
	if (direction != null) {
	    int checks = (int) (Math.ceil(speed) * 2);
	    float localSpeed = speed / checks;
	    for (int i = 0; i < checks; i++) {
		if (!world.isRemote) {
		    TileParticleInjector injector = (TileParticleInjector) tile;
		    injector.checkCollision();
		}
		BlockPos next = getPosition();
		BlockState oldState = world.getBlockState(next);
		boolean isBooster = false;
		if (oldState.getBlock() == DeferredRegisters.blockElectromagneticBooster) {
		    Direction dir = oldState.get(BlockGenericMachine.FACING).getOpposite();
		    FacingDirection face = oldState.get(BlockElectromagneticBooster.FACINGDIRECTION);
		    if (face == FacingDirection.RIGHT) {
			dir = dir.rotateY();
		    } else if (face == FacingDirection.LEFT) {
			dir = dir.rotateYCCW();
		    }
		    if (dir == direction) {
			speed += 0.01 / 3;
		    } else if (dir == direction.getOpposite()) {
			speed -= 0.02;
		    } else {
			speed += 0.01 / 6;
			direction = dir;
			BlockPos floor = getPosition();
			setPosition(floor.getX() + 0.5, floor.getY() + 0.5, floor.getZ() + 0.5);
		    }
		    isBooster = true;
		}
		if (speed < 0) {
		    speed *= -1;
		    direction = direction.getOpposite();
		}
		setPosition(getPosX() + direction.getXOffset() * localSpeed, getPosY(),
			getPosZ() + direction.getZOffset() * localSpeed);
		if (isBooster) {
		    BlockPos positionNow = getPosition();
		    if (world.getBlockState(positionNow).getBlock() == DeferredRegisters.blockElectromagneticSwitch) {
			HashSet<Direction> directions = new HashSet<>();
			for (Direction dir : Direction.values()) {
			    if (dir != Direction.UP && dir != Direction.DOWN && dir != direction.getOpposite()
				    && world.getBlockState(positionNow.offset(dir)).getBlock() == Blocks.AIR) {
				directions.add(dir);
			    }
			}
			TileEntity te = world.getTileEntity(positionNow);
			if (te instanceof TileElectromagneticSwitch) {
			    TileElectromagneticSwitch switchte = (TileElectromagneticSwitch) te;
			    directions.remove(switchte.lastDirection);
			    if (directions.size() > (switchte.lastDirection == null ? 2 : 1)) {
				world.createExplosion(this, getPosX(), getPosY(), getPosZ(), speed, Mode.DESTROY);
				setDead();
				break;
			    }
			    for (Direction dir : directions) {
				switchte.lastDirection = dir;
				direction = dir;
				setPosition(positionNow.getX() + 0.5, positionNow.getY() + 0.5,
					positionNow.getZ() + 0.5);
			    }
			}
		    }
		}
		if (!world.isRemote) {
		    BlockPos getPos = getPosition();
		    BlockState nextState = world.getBlockState(getPos);
		    if (nextState.getBlock() == Blocks.AIR
			    || nextState.getBlock() == DeferredRegisters.blockElectromagneticSwitch) {
			int amount = 0;
			for (Direction of : Direction.values()) {
			    if (world.getBlockState(getPosition().offset(of)).getBlock() instanceof IElectromagnet) {
				amount++;
			    }
			}
			if (amount < 4) {
			    world.createExplosion(this, getPosX(), getPosY(), getPosZ(), speed, Mode.DESTROY);
			    setDead();
			    break;
			}
			BlockState testNextBlock = world.getBlockState(getPos.offset(direction));
			if (testNextBlock.getBlock() instanceof IElectromagnet
				&& testNextBlock.getBlock() != DeferredRegisters.blockElectromagneticSwitch) {
			    Direction checkRot = direction.rotateY();
			    testNextBlock = world.getBlockState(getPos.offset(checkRot));
			    if (testNextBlock.getBlock() == Blocks.AIR
				    || testNextBlock.getBlock() == DeferredRegisters.blockElectromagneticSwitch) {
				BlockPos floor = getPosition();
				direction = checkRot;
				setPosition(floor.getX() + 0.5, floor.getY() + 0.5, floor.getZ() + 0.5);
			    } else {
				checkRot = direction.rotateY().getOpposite();
				testNextBlock = world.getBlockState(getPos.offset(checkRot));
				if (testNextBlock.getBlock() == Blocks.AIR
					|| testNextBlock.getBlock() == DeferredRegisters.blockElectromagneticSwitch) {
				    BlockPos floor = getPosition();
				    direction = checkRot;
				    setPosition(floor.getX() + 0.5, floor.getY() + 0.5, floor.getZ() + 0.5);
				} else {
				    world.createExplosion(this, getPosX(), getPosY(), getPosZ(), speed, Mode.DESTROY);
				    setDead();
				    break;
				}
			    }
			}
		    } else {
			boolean checkIsBooster = nextState.getBlock() == DeferredRegisters.blockElectromagneticBooster
				&& oldState.getBlock() == DeferredRegisters.blockElectromagneticBooster;
			boolean explode = false;
			if (checkIsBooster) {
			    Direction oldDir = oldState.get(BlockGenericMachine.FACING);
			    Direction nextDir = nextState.get(BlockGenericMachine.FACING);
			    if (oldDir != nextDir) {
				FacingDirection face = oldState.get(BlockElectromagneticBooster.FACINGDIRECTION);
				if (face == FacingDirection.RIGHT) {
				    oldDir = oldDir.rotateY();
				} else if (face == FacingDirection.LEFT) {
				    oldDir = oldDir.rotateYCCW();
				}
				if (oldDir != nextDir) {
				    explode = true;
				}
			    }
			} else if (nextState.getBlock() != DeferredRegisters.blockElectromagneticBooster) {
			    explode = true;
			}
			if (explode) {
			    world.createExplosion(this, getPosX(), getPosY(), getPosZ(), speed, Mode.DESTROY);
			    setDead();
			    break;
			}
		    }
		}
	    }
	    speed = Math.min(speed, 1.999f);
	} else {
	    if (ticksExisted > 100) {
		setDead();
	    }
	}
    }

    @Override
    protected void registerData() {
	dataManager.register(DIRECTION, direction);
	dataManager.register(SPEED, speed);
    }

    @Override
    public boolean hasNoGravity() {
	return true;
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
	source = new BlockPos(compound.getInt("sourceX"), compound.getInt("sourceY"), compound.getInt("sourceZ"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
	compound.putInt("sourceX", source.getX());
	compound.putInt("sourceY", source.getY());
	compound.putInt("sourceZ", source.getZ());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
	return NetworkHooks.getEntitySpawningPacket(this);
    }
}