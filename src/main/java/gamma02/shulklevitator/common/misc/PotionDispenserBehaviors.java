package gamma02.shulklevitator.common.misc;

import net.minecraft.potion.Potion;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class PotionDispenserBehaviors {


    private static HashMap<Potion, PotionDispenserBehavior> potions = new HashMap<>();

    /**
     * This method gets the potion set by the creator of the PotionDispenserBehavior
     * SHOULD NOT BE OVERRIDDEN
     * @return the behavior that was the potion refrences
     */
    public static PotionDispenserBehavior getBehavior(Potion potion){
        return potions.get(potion);
    }

    /**
     * Links a Potion to a dispensorBehavior
     * @param potion potion to link behavior to
     * @param behavior linked behavior
     */
    @SuppressWarnings("unused")
    public static void registerPotionBehavior(Potion potion, PotionDispenserBehavior behavior){
        if(potions.containsKey(potion)){
            System.out.println("POTIONS ALREADY CONTAINS LINKED POTION BEHAVIOR FOR " + Registry.POTION.getId(potion) + ". SKIPPING!");
        }else{
            System.out.println("REGISTERING POTION BEHAVIOR FOR " + Registry.POTION.getId(potion));
            potions.put(potion, behavior);
        }

    }

    public static void registerPotionBehavior( PotionDispenserBehavior behavior, Potion... potion){

        for(Potion element : potion){
            if(potions.containsKey(element)){
                System.out.println("POTIONS ALREADY CONTAINS LINKED POTION BEHAVIOR FOR " + Registry.POTION.getId(element) + ". SKIPPING!");
            }else{
                System.out.println("REGISTERING POTION BEHAVIOR FOR " + Registry.POTION.getId(element));
                potions.put(element, behavior);
            }
        }

    }

    public static boolean hasBehavior(Potion potion){
        return potions.containsKey(potion);
    }




}
