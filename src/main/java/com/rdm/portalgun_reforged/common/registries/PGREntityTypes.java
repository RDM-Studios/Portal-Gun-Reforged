package com.rdm.portalgun_reforged.common.registries;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.common.entities.PortalEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PGREntityTypes {
	public static final DeferredRegister<EntityType<?>> PGR_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, PortalGunReforged.MODID);
	
	public static final RegistryObject<EntityType<?>> PORTAL = registerMiscEntity("portal", PortalEntity::new, 0.6F, 1.8F);
	
	private static RegistryObject<EntityType<?>> registerAmbientEntity(String regName, IFactory<?> entityConsumer, float size) {
		RegistryObject<EntityType<?>> entityRegObj = PGR_ENTITY_TYPES.register(regName, () -> EntityType.Builder.of(entityConsumer, EntityClassification.AMBIENT)
				.sized(size, size)
				.build(PortalGunReforged.prefixRL(regName).toString()));		
		return entityRegObj;
	}
	
	private static RegistryObject<EntityType<?>> registerAmbientEntity(String regName, IFactory<?> entityConsumer, float width, float height) {
		RegistryObject<EntityType<?>> entityRegObj = PGR_ENTITY_TYPES.register(regName, () -> EntityType.Builder.of(entityConsumer, EntityClassification.AMBIENT)
				.sized(width, height)
				.build(PortalGunReforged.prefixRL(regName).toString()));		
		return entityRegObj;
	}
	
	private static RegistryObject<EntityType<?>> registerAmbientWaterEntity(String regName, IFactory<?> entityConsumer, float size) {
		RegistryObject<EntityType<?>> entityRegObj = PGR_ENTITY_TYPES.register(regName, () -> EntityType.Builder.of(entityConsumer, EntityClassification.WATER_AMBIENT)
				.sized(size, size)
				.build(PortalGunReforged.prefixRL(regName).toString()));		
		return entityRegObj;
	}
	
	private static RegistryObject<EntityType<?>> registerAmbientWaterEntity(String regName, IFactory<?> entityConsumer, float width, float height) {
		RegistryObject<EntityType<?>> entityRegObj = PGR_ENTITY_TYPES.register(regName, () -> EntityType.Builder.of(entityConsumer, EntityClassification.WATER_AMBIENT)
				.sized(width, height)
				.build(PortalGunReforged.prefixRL(regName).toString()));		
		return entityRegObj;
	}
	
	private static RegistryObject<EntityType<?>> registerCreatureEntity(String regName, IFactory<?> entityConsumer, float size) {
		RegistryObject<EntityType<?>> entityRegObj = PGR_ENTITY_TYPES.register(regName, () -> EntityType.Builder.of(entityConsumer, EntityClassification.CREATURE)
				.sized(size, size)
				.build(PortalGunReforged.prefixRL(regName).toString()));		
		return entityRegObj;
	}
	
	private static RegistryObject<EntityType<?>> registerCreatureEntity(String regName, IFactory<?> entityConsumer, float width, float height) {
		RegistryObject<EntityType<?>> entityRegObj = PGR_ENTITY_TYPES.register(regName, () -> EntityType.Builder.of(entityConsumer, EntityClassification.CREATURE)
				.sized(width, height)
				.build(PortalGunReforged.prefixRL(regName).toString()));		
		return entityRegObj;
	}
	
	private static RegistryObject<EntityType<?>> registerCreatureWaterEntity(String regName, IFactory<?> entityConsumer, float size) {
		RegistryObject<EntityType<?>> entityRegObj = PGR_ENTITY_TYPES.register(regName, () -> EntityType.Builder.of(entityConsumer, EntityClassification.WATER_CREATURE)
				.sized(size, size)
				.build(PortalGunReforged.prefixRL(regName).toString()));		
		return entityRegObj;
	}
	
	private static RegistryObject<EntityType<?>> registerCreatureWaterEntity(String regName, IFactory<?> entityConsumer, float width, float height) {
		RegistryObject<EntityType<?>> entityRegObj = PGR_ENTITY_TYPES.register(regName, () -> EntityType.Builder.of(entityConsumer, EntityClassification.WATER_CREATURE)
				.sized(width, height)
				.build(PortalGunReforged.prefixRL(regName).toString()));		
		return entityRegObj;
	}
	
	private static RegistryObject<EntityType<?>> registerMiscEntity(String regName, IFactory<?> entityConsumer, float size) {
		RegistryObject<EntityType<?>> entityRegObj = PGR_ENTITY_TYPES.register(regName, () -> EntityType.Builder.of(entityConsumer, EntityClassification.MISC)
				.sized(size, size)
				.build(PortalGunReforged.prefixRL(regName).toString()));		
		return entityRegObj;
	}
	
	private static RegistryObject<EntityType<?>> registerMiscEntity(String regName, IFactory<?> entityConsumer, float width, float height) {
		RegistryObject<EntityType<?>> entityRegObj = PGR_ENTITY_TYPES.register(regName, () -> EntityType.Builder.of(entityConsumer, EntityClassification.MISC)
				.sized(width, height)
				.build(PortalGunReforged.prefixRL(regName).toString()));		
		return entityRegObj;
	}
	
	private static RegistryObject<EntityType<?>> registerMonsterEntity(String regName, IFactory<?> entityConsumer, float size) {
		RegistryObject<EntityType<?>> entityRegObj = PGR_ENTITY_TYPES.register(regName, () -> EntityType.Builder.of(entityConsumer, EntityClassification.MONSTER)
				.sized(size, size)
				.build(PortalGunReforged.prefixRL(regName).toString()));		
		return entityRegObj;
	}
	
	private static RegistryObject<EntityType<?>> registerMonsterEntity(String regName, IFactory<?> entityConsumer, float width, float height) {
		RegistryObject<EntityType<?>> entityRegObj = PGR_ENTITY_TYPES.register(regName, () -> EntityType.Builder.of(entityConsumer, EntityClassification.MONSTER)
				.sized(width, height)
				.build(PortalGunReforged.prefixRL(regName).toString()));		
		return entityRegObj;
	}
	
	private static RegistryObject<EntityType<?>> registerEntity(String regName, IFactory<?> entityConsumer, EntityClassification classification, float size) {
		RegistryObject<EntityType<?>> entityRegObj = PGR_ENTITY_TYPES.register(regName, () -> EntityType.Builder.of(entityConsumer, classification)
				.sized(size, size)
				.build(PortalGunReforged.prefixRL(regName).toString()));		
		return entityRegObj;
	}
	
	private static RegistryObject<EntityType<?>> registerEntity(String regName, IFactory<?> entityConsumer, EntityClassification classification, float width, float height) {
		RegistryObject<EntityType<?>> entityRegObj = PGR_ENTITY_TYPES.register(regName, () -> EntityType.Builder.of(entityConsumer, classification)
				.sized(width, height)
				.build(PortalGunReforged.prefixRL(regName).toString()));		
		return entityRegObj;
	}

}
