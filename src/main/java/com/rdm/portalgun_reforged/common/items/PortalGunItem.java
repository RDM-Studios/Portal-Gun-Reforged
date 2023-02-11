package com.rdm.portalgun_reforged.common.items;

import java.util.List;

import com.rdm.portalgun_reforged.common.entities.PortalEntity;
import com.rdm.portalgun_reforged.common.items.base.AnimatableSyncableItem;
import com.rdm.portalgun_reforged.common.registries.PGREntityTypes;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class PortalGunItem extends AnimatableSyncableItem {
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private AnimationController<PortalGunItem> mainController = new AnimationController<PortalGunItem>(this, getControllerName(), getTransitioningTicks(), this::mainPredicate);
	private Mode portalGunMode = Mode.NORMAL;
	private int useDuration = 0;
	
	public PortalGunItem(Properties pProperties) {
		super(pProperties);
		GeckoLibNetwork.registerSyncable(this);
	}
	
	@Override
	protected String getControllerName() {
		return "portalGunController";
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(mainController);
	}

	@Override
	public <E extends Item & IAnimatable> PlayState mainPredicate(AnimationEvent<E> event) {
		if (getUseDuration() > 0) event.getController().setAnimation(new AnimationBuilder().addAnimation("portal_shoot", EDefaultLoopTypes.PLAY_ONCE));
		return PlayState.CONTINUE;
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	protected int getTransitioningTicks() {
		return 1;
	}
	
	@Override
	public void onAnimationSync(int id, int state) {
		if (state == 0x0) {
			final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, getControllerName());
			assert controller != null;
			if (controller != null) {
				if (controller.getAnimationState() == AnimationState.Stopped) {
					controller.markNeedsReload();
					controller.setAnimation(new AnimationBuilder().addAnimation("portal_shoot", EDefaultLoopTypes.PLAY_ONCE));
				}
			}
		}
	}
	
	public int getUseDuration() {
		return useDuration;
	}
	
	public void setUseDuration(int useDuration) {
		this.useDuration = useDuration;
	}
	
	public byte getPortalGunState() {
		return portalGunMode.getState();
	}
	
	public void setPortalGunState(byte state) {
		portalGunMode.setState(state);
	}
	
	public Mode getPortalGunMode() {
		return portalGunMode;
	}
	
	public void setPortalGunMode(Mode mode) {
		this.portalGunMode = mode;
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
		return true;
	}
	
	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		setUseDuration(2);
		if (getUseDuration() > 0) setUseDuration(getUseDuration() - 1);
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
		ItemStack portalGunStack = pPlayer.getMainHandItem();
		
		if (getPortalGunMode().equals(Mode.NORMAL)) {
			setPortalGunState((byte) 2);
			setUseDuration(2);
			PortalEntity portal = (PortalEntity) PGREntityTypes.PORTAL.get().create(pLevel);
			
			
		} else if (getPortalGunMode().equals(Mode.CARRY_ENTITY)) {
			
		}
		
		pPlayer.getCooldowns().addCooldown(this, 4);
		pPlayer.awardStat(Stats.ITEM_USED.get(this));
		if (getUseDuration() > 0) setUseDuration(getUseDuration() - 1);
		return ActionResult.pass(portalGunStack);
	}
	
	@Override
	public void appendHoverText(ItemStack pStack, World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
		super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
		
		pTooltip.add(new StringTextComponent("Current Mode: ")
				.withStyle(TextFormatting.DARK_AQUA)
				.append(new StringTextComponent(getPortalGunMode().getName().toUpperCase())
						.withStyle(getPortalGunMode().equals(Mode.NORMAL) ? TextFormatting.AQUA : TextFormatting.GOLD).withStyle(TextFormatting.BOLD)));
	}
		
	public void spawnPortal(Mode portalGunMode, byte portalModeState) {
		
	}
	
	public void removePortals() {
		
	}
	
	public enum Mode {
		NORMAL("normal", (byte) 0),
		CARRY_ENTITY("carry_entity", (byte) 0);
		
		private final String name;
		private byte state;
		
		Mode(String name, byte state) {
			this.name = name;
			this.state = state;
		}
		
		public String getName() {
			return name;
		}
		
		public byte getState() {
			return state;
		}
		
		public void setState(byte state) {
			this.state = state;
		}
	}
}
