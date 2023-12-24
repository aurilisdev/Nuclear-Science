package nuclearscience.common.entity;

import java.util.HashSet;

import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import nuclearscience.api.fusion.IElectromagnet;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.common.block.BlockElectromagneticBooster;
import nuclearscience.common.block.facing.FacingDirection;
import nuclearscience.common.tile.TileElectromagneticSwitch;
import nuclearscience.common.tile.TileParticleInjector;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceEntities;

public class EntityParticle extends Entity {
	private static final DataParameter<Direction> DIRECTION = EntityDataManager.defineId(EntityParticle.class, DataSerializers.DIRECTION);
	private static final DataParameter<Float> SPEED = EntityDataManager.defineId(EntityParticle.class, DataSerializers.FLOAT);
	private Direction direction = Direction.UP;
	public float speed = 0.02f;
	public BlockPos source = BlockPos.ZERO;

	public EntityParticle(EntityType<?> entityTypeIn, World worldIn) {
		super(NuclearScienceEntities.ENTITY_PARTICLE.get(), worldIn);
	}

	public EntityParticle(Direction direction, World worldIn, Location pos) {
		this(NuclearScienceEntities.ENTITY_PARTICLE.get(), worldIn);
		setPos(pos.x(), pos.y(), pos.z());
		this.direction = direction;
		noCulling = true;
		if (worldIn.isClientSide) {
			setViewScale(4);
		}
	}

	@Override
	public void tick() {
		TileEntity tile = level.getBlockEntity(source);
		if (!level.isClientSide) {
			if (!(tile instanceof TileParticleInjector)) {
				remove(false);
				return;
			}
			((TileParticleInjector) tile).addParticle(this);
			if (direction == null) {
				direction = Direction.UP;
			}
			entityData.set(DIRECTION, direction);

			entityData.set(SPEED, speed);
		} else if (!entityData.isEmpty()) {
			direction = entityData.get(DIRECTION);
			speed = entityData.get(SPEED);
		}
		RadiationSystem.emitRadiationFromLocation(level, new Location(blockPosition()), 1.5, 1000);
		if (direction != null && direction != Direction.UP) {
			int checks = (int) (Math.ceil(speed) * 2);
			float localSpeed = speed / checks;
			for (int i = 0; i < checks; i++) {
				if (!level.isClientSide) {
					TileParticleInjector injector = (TileParticleInjector) tile;
					injector.checkCollision();
				} else {
					level.addParticle(new RedstoneParticleData(1, 1, 1, 5), getX(), getY(), getZ(), 0, 0, 0);
				}
				BlockPos next = blockPosition();
				BlockState oldState = level.getBlockState(next);
				boolean isBooster = false;
				if (oldState.getBlock() == NuclearScienceBlocks.blockElectromagneticBooster) {
					Direction dir = oldState.getValue(GenericEntityBlock.FACING).getOpposite();
					FacingDirection face = oldState.getValue(BlockElectromagneticBooster.FACINGDIRECTION);
					if (face == FacingDirection.RIGHT) {
						dir = dir.getClockWise();
					} else if (face == FacingDirection.LEFT) {
						dir = dir.getCounterClockWise();
					}
					if (dir == direction) {
						speed += 0.01 / 3;
					} else if (dir == direction.getOpposite()) {
						speed -= 0.02;
					} else {
						speed += 0.01 / 6;
						direction = dir;
						BlockPos floor = blockPosition();
						setPos(floor.getX() + 0.5, floor.getY() + 0.5, floor.getZ() + 0.5);
					}
					isBooster = true;
				}
				if (speed < 0) {
					speed *= -1;
					direction = direction.getOpposite();
				}
				setPos(getX() + direction.getStepX() * localSpeed, getY(), getZ() + direction.getStepZ() * localSpeed);
				if (isBooster) {
					BlockPos positionNow = blockPosition();
					if (level.getBlockState(positionNow).getBlock() == NuclearScienceBlocks.blockElectromagneticSwitch) {
						HashSet<Direction> directions = new HashSet<>();
						for (Direction dir : Direction.values()) {
							if (dir != Direction.UP && dir != Direction.DOWN && dir != direction.getOpposite() && level.getBlockState(positionNow.relative(dir)).getBlock() == Blocks.AIR) {
								directions.add(dir);
							}
						}
						TileEntity te = level.getBlockEntity(positionNow);
						if (te instanceof TileElectromagneticSwitch) {
							TileElectromagneticSwitch switchte = (TileElectromagneticSwitch) te;
							directions.remove(switchte.lastDirection);
							if (directions.size() > (switchte.lastDirection == null ? 2 : 1)) {
								level.explode(this, getX(), getY(), getZ(), speed, Mode.DESTROY);
								removeAfterChangingDimensions();
								break;
							}
							for (Direction dir : directions) {
								switchte.lastDirection = dir;
								direction = dir;
								setPos(positionNow.getX() + 0.5, positionNow.getY() + 0.5, positionNow.getZ() + 0.5);
							}
						}
					}
				}
				if (!level.isClientSide) {
					BlockPos getPos = blockPosition();
					BlockState nextState = level.getBlockState(getPos);
					if (nextState.getBlock() == Blocks.AIR || nextState.getBlock() == NuclearScienceBlocks.blockElectromagneticSwitch) {
						int amount = 0;
						for (Direction of : Direction.values()) {
							if (level.getBlockState(blockPosition().relative(of)).getBlock() instanceof IElectromagnet) {
								amount++;
							}
						}
						if (amount < 4) {
							level.explode(this, getX(), getY(), getZ(), speed, Mode.DESTROY);
							removeAfterChangingDimensions();
							break;
						}
						BlockState testNextBlock = level.getBlockState(getPos.relative(direction));
						if (testNextBlock.getBlock() instanceof IElectromagnet && testNextBlock.getBlock() != NuclearScienceBlocks.blockElectromagneticSwitch) {
							Direction checkRot = direction.getClockWise();
							testNextBlock = level.getBlockState(getPos.relative(checkRot));
							if (testNextBlock.getBlock() == Blocks.AIR || testNextBlock.getBlock() == NuclearScienceBlocks.blockElectromagneticSwitch) {
								BlockPos floor = blockPosition();
								direction = checkRot;
								setPos(floor.getX() + 0.5, floor.getY() + 0.5, floor.getZ() + 0.5);
							} else {
								checkRot = direction.getClockWise().getOpposite();
								testNextBlock = level.getBlockState(getPos.relative(checkRot));
								if (testNextBlock.getBlock() == Blocks.AIR || testNextBlock.getBlock() == NuclearScienceBlocks.blockElectromagneticSwitch) {
									BlockPos floor = blockPosition();
									direction = checkRot;
									setPos(floor.getX() + 0.5, floor.getY() + 0.5, floor.getZ() + 0.5);
								} else {
									level.explode(this, getX(), getY(), getZ(), speed, Mode.DESTROY);
									removeAfterChangingDimensions();
									break;
								}
							}
						}
					} else {
						boolean checkIsBooster = nextState.getBlock() == NuclearScienceBlocks.blockElectromagneticBooster && oldState.getBlock() == NuclearScienceBlocks.blockElectromagneticBooster;
						boolean explode = false;
						if (checkIsBooster) {
							Direction oldDir = oldState.getValue(GenericEntityBlock.FACING);
							Direction nextDir = nextState.getValue(GenericEntityBlock.FACING);
							if (oldDir != nextDir) {
								FacingDirection face = oldState.getValue(BlockElectromagneticBooster.FACINGDIRECTION);
								if (face == FacingDirection.RIGHT) {
									oldDir = oldDir.getClockWise();
								} else if (face == FacingDirection.LEFT) {
									oldDir = oldDir.getCounterClockWise();
								}
								if (oldDir != nextDir) {
									explode = true;
								}
							}
						} else if (nextState.getBlock() != NuclearScienceBlocks.blockElectromagneticBooster) {
							explode = true;
						}
						if (explode) {
							level.explode(this, getX(), getY(), getZ(), speed, Mode.DESTROY);
							removeAfterChangingDimensions();
							break;
						}
					}
				}
			}
			speed = Math.min(speed, 1.999f);
		} else if (tickCount > 100) {
			removeAfterChangingDimensions();
		}
	}

	@Override
	protected void defineSynchedData() {
		if (direction == null) {
			direction = Direction.UP;
		}
		entityData.define(DIRECTION, direction);
		entityData.define(SPEED, speed);
	}

	@Override
	public boolean isNoGravity() {
		return true;
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT compound) {
		source = new BlockPos(compound.getInt("sourceX"), compound.getInt("sourceY"), compound.getInt("sourceZ"));
	}

	@Override
	public ITextComponent getName() {
		return new TranslationTextComponent("entity.particle");
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT compound) {
		compound.putInt("sourceX", source.getX());
		compound.putInt("sourceY", source.getY());
		compound.putInt("sourceZ", source.getZ());
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}