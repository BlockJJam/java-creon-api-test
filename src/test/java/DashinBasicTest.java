import com4j.Com4jObject;
import com4j.EventCookie;
import dashin.cpdib.IDib;
import dashin.cpdib.events._IDibEvents;
import dashin.cputil.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class DashinBasicTest {

    @BeforeEach
    void loginCreon(){
        loginAfterRunCreon();
    }

    @Test
    void confirm_symbolcode(){
        // given
        ICpStockCode iCpStockCode = ClassFactory.createCpStockCode();

        // when
        String strName = iCpStockCode.codeToName("A056080");

        // then
        Assertions.assertEquals("유진로봇", strName);
    }

    @Test
    void call_all_symbolcode(){
        // given
        ICpStockCode iCpStockCode = ClassFactory.createCpStockCode();
        ICpCodeMgr codeMgr = ClassFactory.createCpCodeMgr();

        // when
        Object[] market = (Object[]) codeMgr.getStockListByMarket(CPE_MARKET_KIND.CPC_MARKET_KOSPI);

        // then
        System.out.println("전체 종목수: "+ market.length);

        for( Object e : market){
            System.out.println(iCpStockCode.codeToName(e.toString()));
        }
    }

    @Test
    void run_cybos_plus_and_login(){
        loginAfterRunCreon();
    }



    @Test
    void is_connect_cybos(){
        ICpCybos cpCybos = ClassFactory.createCpCybos();

        Assertions.assertEquals(1, cpCybos.isConnect());
    }

    @Test
    void stockcur_realtime_test(){
        // given
        IDib iDib = dashin.cpdib.ClassFactory.createStockCur();
        iDib.setInputValue(0,"A005930");
        EventCookie cookie = null;
        // when
        try{
            cookie = iDib.advise(_IDibEvents.class, new _IDibEvents() {
                @Override
                public void received() {
                    Object code = iDib.getHeaderValue(0);
                    System.out.println(code.toString());
                }
            });
            iDib.subscribe();

        }catch(Exception e){
            e.printStackTrace();
            if(cookie != null)
                cookie.close();
        }

        // then
    }

    private void loginAfterRunCreon() {
        try{
            Runtime.getRuntime().exec("taskkill /IM CpStart.exe /F");
            Runtime.getRuntime().exec("taskkill /IM coStarter.exe /F");
            Runtime.getRuntime().exec("taskkill /IM DibServer.exe /F");
            Runtime.getRuntime().exec("wmic process where \"name like \\'%coStarter%\\'\" call terminate");
            Runtime.getRuntime().exec("wmic process where \"name like \\'%CpStart%\\'\" call terminate");            Runtime.getRuntime().exec("wmic process where \"name like \\'%CpStart%\\'\" call terminate");
            Runtime.getRuntime().exec("wmic process where \"name like \\'%Dib%\\'\" call terminate");


            Process p = Runtime.getRuntime().exec("C:\\CREON\\STARTER\\coStarter.exe /prj:cp /id:khk7129 /pwd:woals1! /pwdcert:khk7129!@# /autostart");
            p.waitFor();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
