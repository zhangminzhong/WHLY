package org.whut.meterManagement.smsmeterlib.receive;

import java.util.ArrayList;

/**
 * Created by zhang_minzhong on 2016/12/12.
 */

/// <summary>
/// PDA设备信息
/// </summary>
public class PDAInfo {
    private String pdaID;
    private String meterID;
    private String[] arInfo;

    public String getMeterID() {
        return meterID;
    }

    public String getPdaID() {
        return pdaID;
    }

    public void setPdaID(String pdaID) {
        this.pdaID = pdaID;
    }

    public String[] getArInfo() {
        return arInfo;
    }

    public PDAInfo()
    {
        pdaID = "";
        meterID = "";
    }
    
    public void getFromStr(String dstr,byte funcCode){
        String strTemp = dstr;
        //取得表号
        meterID = "";
        if(dstr.length()<26)
            return;
        String temp = dstr.substring(0,26);
        meterID = myConvert(temp);
        //取得其他参数
        if(dstr.length()<28)
            return;
        strTemp = dstr.substring(26);
        if(funcCode==0x3C){
            ArrayList<String> alInfo = new ArrayList<String>();
            int L;
            while(true){
                L = Integer.parseInt(dstr.substring(0,2),16);
                if(strTemp.length()<(2+L*2))
                    break;
                temp = strTemp.substring(2,2+L*2);
                alInfo.add(myConvert(temp));
                if(strTemp.length()==(2+L*2))
                    break;
                else
                    strTemp = strTemp.substring(2+L*2);
            }
            //将ArrayLIst中的字符串放入arInfo数组
            arInfo = new String[alInfo.size()];
            for(int i=0;i<arInfo.length;i++)
                arInfo[i] = alInfo.get(i);
        }
        else{
            arInfo = new String[1];
            arInfo[0] = myConvert(strTemp);
        }
    }

    private String myConvert(String s) {
        String rst = "";
        if((s.length()%2)!=0)
            return rst;
        int N = s.length()/2;
        int B;
        for(int i=0;i<N;i++){
            B = Integer.parseInt(s.substring(i*2,i*2+2),16);
            rst += (char)B;
        }
        return rst;
    }
}
