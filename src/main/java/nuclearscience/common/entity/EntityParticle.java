package nuclearscience.common.entity;

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
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import nuclearscience.DeferredRegisters;
import nuclearscience.api.fusion.IElectromagnet;
import nuclearscience.common.block.electromagneticbooster.BlockElectromagneticBooster;
import nuclearscience.common.block.electromagneticbooster.FacingDirection;
import nuclearscience.common.tile.TileParticleInjector;

public class EntityParticle extends Entity {
	private static final DataParameter<Direction> DIRECTION = EntityDataManager.createKey(EntityParticle.class, DataSerializers.DIRECTION);
	private static final DataParameter<Float> SPEED = EntityDataManager.createKey(EntityParticle.class, DataSerializers.FLOAT);
	private Direction direction;
	private float speed = 0.02f;
	public BlockPos source = BlockPos.ZERO;

	public EntityParticle(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public EntityParticle(Direction direction, World worldIn, Vector3f pos) {
		this(DeferredRegisters.ENTITY_PARTICLE.get(), worldIn);
		this.forceSetPosition(pos.getX(), pos.getY(), pos.getZ());
		this.direction = direction;
		ignoreFrustumCheck = true;
		setRenderDistanceWeight(4);
	}

	@Override
	public void tick() {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(source);
			if (tile instanceof TileParticleInjector) {
				((TileParticleInjector) tile).addParticle(this);
			} else {
				remove();
			}
			dataManager.set(DIRECTION, direction);
			dataManager.set(SPEED, speed);
		} else {
			direction = dataManager.get(DIRECTION);
			speed = dataManager.get(SPEED);
		}
		if (direction != null) {
			int checks = (int) (Math.ceil(speed) * 2);
			float localSpeed = speed / (float) checks;
			for (int i = 0; i < checks; i++) {
				BlockPos next = getPosition();
				BlockState oldState = world.getBlockState(next);
				if (!world.isRemote) {
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
					}
				}
				if (speed < 0) {
					speed *= -1;
					direction = direction.getOpposite();
				}
				setPosition(getPosX() + direction.getXOffset() * localSpeed, getPosY(), getPosZ() + direction.getZOffset() * localSpeed);

				if (!world.isRemote) {
					BlockState nextState = world.getBlockState(getPosition());
					if (nextState.getBlock() == Blocks.AIR) {
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
					} else {
						boolean checkIsBooster = nextState.getBlock() == DeferredRegisters.blockElectromagneticBooster && oldState.getBlock() == DeferredRegisters.blockElectromagneticBooster;
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