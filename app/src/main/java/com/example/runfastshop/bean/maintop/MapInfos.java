package com.example.runfastshop.bean.maintop;

/**
 * Created by huiliu on 2017/9/4.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class MapInfos {
    private MapInfo map;

    private int agentId;

    public void setMap(MapInfo map){
        this.map = map;
    }
    public MapInfo getMap(){
        return this.map;
    }
    public void setAgentId(int agentId){
        this.agentId = agentId;
    }
    public int getAgentId(){
        return this.agentId;
    }
}
