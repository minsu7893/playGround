package com.playGround.Service;

public class PlayGroundService {

    public String ssh(){

        try{

            Ssh ssh = new Ssh();
            ssh.

            LandExample landModel = new LandExample();

            landModel.createCriteria().andCortarEqualTo("강남구");

            ObjectMapper mapper = new ObjectMapper();

            List<Land> land = landMapper.selectByExample(landModel);

            for(int i = 0 ; i < land.size() ; i++){
                System.out.println("sql select : " + i);
                System.out.println(mapper.writeValueAsString(land.get(i)));
            }

        }catch (Exception e){
            System.out.println();
        }
    }

}
