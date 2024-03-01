package yee.pltision.blasttech.structures.blastfurnace;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import yee.pltision.blasttech.block.DisplayedFurnaceEntity;
import yee.pltision.blasttech.structures.StructureEntity;

public class BlastFurnaceEntity implements StructureEntity, Container {

    public static Item[] TEMPLATE_BAR={
            Items.BLUE_STAINED_GLASS_PANE,
            Items.CYAN_STAINED_GLASS_PANE,
            Items.GRAY_STAINED_GLASS_PANE,
            Items.BROWN_STAINED_GLASS_PANE,
            Items.RED_STAINED_GLASS_PANE,
            Items.ORANGE_STAINED_GLASS_PANE,
            Items.YELLOW_STAINED_GLASS_PANE,
            Items.WHITE_STAINED_GLASS_PANE,
    };

    CompoundTag temperatureBarTag,temperatureMessageTag,temperatureDisplay, temperatureBarItemTag;

    public static String EXTINGUISHED="未点燃";
    public static String LIGHTING="正在点燃...";
    public static String HOT="炽热";
    public static String RED="红热";
    public static String ORANGE="橙";
    public static String YELLOW="黄";
    public static String WHITE="白";
    public static String BRILLIANT_WHITE="炽白";

    public static String EXTINGUISHED_COLOR="gray";
    public static String LIGHTING_COLOR="gray";
    public static String HOT_COLOR="gray";
    public static String RED_COLOR="dark_red";
    public static String ORANGE_COLOR="gold";
    public static String YELLOW_COLOR="yellow";
    public static String WHITE_COLOR="white";
    public static String BRILLIANT_WHITE_COLOR="red";

    int height;
    int testingFloor = 0;
    float temperature;
    float maxTemperature =1600;

    int coal,ore,result;

    public BlastFurnaceEntity() {
        temperatureDisplay=new CompoundTag();
        temperatureBarItemTag =new CompoundTag();
        temperatureBarItemTag.putString("id","blasttech:gui");
        temperatureBarTag=new CompoundTag();
        temperatureBarTag.put("BlastItem",temperatureBarItemTag);
        temperatureBarTag.put("display",temperatureDisplay);
        temperatureMessageTag=new CompoundTag();
        temperatureMessageTag.put("display",temperatureDisplay);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(new SimpleMenuProvider((p_39954_, p_39955_, p_39956_) -> {
                return new BlastFurnaceMenu(this,p_39955_, p_39954_);
            }, Component.literal("高炉")));

            return InteractionResult.CONSUME;
        }
    }

    public void tick(Level level, BlockPos pos, BlockState state, DisplayedFurnaceEntity entity){
        temperature+=1;
        if(temperature> maxTemperature)temperature=0;

    }

    public boolean createTicker(){return true;}

    @Override
    public CompoundTag serializeNBT() {
        var tag=new CompoundTag();
        tag.putFloat("temperature",temperature);
        tag.putInt("height",height);
        tag.putInt("coal",coal);
        tag.putInt("ore",ore);
        tag.putInt("result",result);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        temperature=tag.getFloat("temperature");
        height=tag.getInt("height");
        coal=tag.getInt("coal");
        ore=tag.getInt("ore");
        result=tag.getInt("result");
    }

    public static boolean testButton(LevelAccessor level, BlockPos origin, Direction facing, BlockState stoneBricks) {
//        if(level.getBlockState(origin)!= Blocks.AIR.defaultBlockState())return false;
//        System.out.println("yee");
        for (Direction dir : Direction.values()) {
            if (dir != Direction.UP && dir != facing) {
                if (level.getBlockState(origin.relative(dir)) != stoneBricks) return false;
            }
        }
        return true;
    }

    @Override
    public int getContainerSize() {
        return 36;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        switch (slot){
            case 9+2-1:{
                ItemStack item=new ItemStack(Items.COAL);
                item.setCount(114);
                return item;
            }
            case 9+5-1:{
                ItemStack item=new ItemStack(Items.RAW_IRON);
                item.setCount(514);
                return item;
            }
            case 9+8-1:{
                ItemStack item=new ItemStack(Items.IRON_INGOT);
                item.setCount(1919810);
                return item;
            }
            case 27:{
                ItemStack item= new ItemStack(Items.CAMPFIRE);
                temperatureBarTag.put("display",setupDisplay(temperatureDisplay, (int) temperature));
                temperatureBarItemTag.putString("gui",getTemperatureGui());
                temperatureBarTag.put("BlastItem",temperatureBarItemTag);
                item.setTag(temperatureBarTag);

                return item;
            }
            default:{
                if(slot>27){
                    ItemStack item=new ItemStack(slot-28>temperature/(maxTemperature /8)?Items.BLACK_STAINED_GLASS_PANE:TEMPLATE_BAR[slot-28]);

                    temperatureMessageTag.put("display",setupDisplay(temperatureDisplay, (int) temperature));

                    item.setTag(temperatureMessageTag);

                    return item;
                }
                return ItemStack.EMPTY;
            }
        }
    }
    public String getTemperatureGui(){
        return "bt_temperature"+(Integer.max(1,Integer.min((int)(temperature/(maxTemperature/29)),28)));
    }
    public static CompoundTag setupDisplay(final CompoundTag displayTag,final int temperature){

        if(temperature>1500){
            displayTag.putString("Name",getMessageString(BRILLIANT_WHITE,BRILLIANT_WHITE_COLOR,new StringBuilder().append(" ").append(temperature).append("°C")).toString());
        }
        else if(temperature>1300){
            displayTag.putString("Name",getMessageString(WHITE,WHITE_COLOR,new StringBuilder().append(" ").append(temperature).append("°C")).toString());
        }
        else if(temperature>1000){
            displayTag.putString("Name",getMessageString(YELLOW,YELLOW_COLOR,new StringBuilder().append(" ").append(temperature).append("°C")).toString());
        }
        else if(temperature>700){
            displayTag.putString("Name",getMessageString(ORANGE,ORANGE_COLOR,new StringBuilder().append(" ").append(temperature).append("°C")).toString());
        }
        else if(temperature>400){
            displayTag.putString("Name",getMessageString(RED,RED_COLOR,new StringBuilder().append(" ").append(temperature).append("°C")).toString());
        }
        else if(temperature>150){
            displayTag.putString("Name",getMessageString(HOT,HOT_COLOR,new StringBuilder().append(" ").append(temperature).append("°C")).toString());
        }
        else if(temperature>0){
            displayTag.putString("Name",getMessageString(LIGHTING,LIGHTING_COLOR,new StringBuilder()).toString());
        }
        else{
            displayTag.putString("Name",getMessageString(EXTINGUISHED,EXTINGUISHED_COLOR,new StringBuilder()).toString());
        }
        return displayTag;
    }
    public static StringBuilder getMessageString(String text,String color,StringBuilder textAppend){
        return new StringBuilder("{\"text\":\"").append(text).append(textAppend).append("\",\"color\":\"").append(color).append("\",\"italic\":\"false\"}");
    }

    @Override
    public @NotNull ItemStack removeItem(int p_18942_, int p_18943_) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int p_18951_) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int p_18944_, @NotNull ItemStack p_18945_) {
    }

    @Override
    public void setChanged() {
    }

    @Override
    public boolean stillValid(@NotNull Player p_18946_) {
        return true;
    }

    @Override
    public void clearContent() {
    }

}
