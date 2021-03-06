package me.jacky1356400.playerstatues.util;

import net.minecraft.nbt.NBTTagCompound;

public class StatueParameters {
    float armLeftA;
    float armLeftB;
    float armRightA;
    float armRightB;
    float legLeftA;
    float legLeftB;
    float legRightA;
    float legRightB;
    float headA;
    float headB;
    float bodyA;
    float bodyB;
    float itemLeftA;
    float itemRightA;

    public StatueParameters(){
        armLeftA=2.0f/3;
        armRightA=2.0f/3;
        armLeftB=0.0f;
        armRightB=0.0f;

        legLeftA=1.0f;
        legLeftB=0.5f;
        legRightA=1.0f;
        legRightB=0.5f;

        headA=0.5f;
        headB=0.5f;
        bodyA=0.5f;
        bodyB=0.5f;

        itemLeftA=1.0f;
    }

    void copyFrom(StatueParameters s){
        armLeftA=s.armLeftA;
        armLeftB=s.armLeftB;
        armRightA=s.armRightA;
        armRightB=s.armRightB;
        legLeftA=s.legLeftA;
        legLeftB=s.legLeftB;
        legRightA=s.legRightA;
        legRightB=s.legRightB;
        headA=s.headA;
        headB=s.headB;
        bodyA=s.bodyA;
        bodyB=s.bodyB;
        itemLeftA=s.itemLeftA;
        itemRightA=s.itemRightA;
    }

    public void readFromNBT(NBTTagCompound tag) {
        if(!tag.hasKey("ala")) return;

        armLeftA=tag.getFloat("ala");
        armLeftB=tag.getFloat("alb");
        armRightA=tag.getFloat("ara");
        armRightB=tag.getFloat("arb");
        legLeftA=tag.getFloat("lla");
        legLeftB=tag.getFloat("llb");
        legRightA=tag.getFloat("lra");
        legRightB=tag.getFloat("lrb");
        headA=tag.getFloat("ha");
        headB=tag.getFloat("hb");
        bodyA=tag.getFloat("ba");
        bodyB=tag.getFloat("bb");
        itemLeftA=tag.getFloat("ila");
        itemRightA=tag.getFloat("ira");

    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.setFloat("ala",armLeftA);
        tag.setFloat("alb",armLeftB);
        tag.setFloat("ara",armRightA);
        tag.setFloat("arb",armRightB);
        tag.setFloat("lla",legLeftA);
        tag.setFloat("llb",legLeftB);
        tag.setFloat("lra",legRightA);
        tag.setFloat("lrb",legRightB);
        tag.setFloat("ha",headA);
        tag.setFloat("hb",headB);
        tag.setFloat("ba",bodyA);
        tag.setFloat("bb",bodyB);
        tag.setFloat("ila",itemLeftA);
        tag.setFloat("ira",itemRightA);
    }


}
