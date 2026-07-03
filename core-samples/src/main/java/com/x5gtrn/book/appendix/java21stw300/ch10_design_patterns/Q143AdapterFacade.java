package com.x5gtrn.book.appendix.java21stw300.ch10_design_patterns;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q143AdapterFacade implements Sample {
    // 外部 API（変更できない前提）
    static final class LegacyWeatherApi {
        String getTemperatureFahrenheit(String city){ return city.equals("Tokyo") ? "77.0" : "60.0"; }
    }
    // Adapter: 自分たちのインターフェースに合わせ、外部依存を隔離
    interface WeatherService { double celsius(String city); }
    static final class WeatherAdapter implements WeatherService {
        private final LegacyWeatherApi api = new LegacyWeatherApi();
        public double celsius(String city){
            double f = Double.parseDouble(api.getTemperatureFahrenheit(city));
            return Math.round((f - 32) * 5.0 / 9.0 * 10) / 10.0;
        }
    }
    public String chapter(){return "10_Design_Patterns";}
    public int problem(){return 143;}
    public String title(){return "Adapter/Facade と外部 API 隔離";}
    public void run(){
        WeatherService svc = new WeatherAdapter();
        System.out.println("Tokyo = " + svc.celsius("Tokyo") + "℃");
        System.out.println("Osaka = " + svc.celsius("Osaka") + "℃");
    }
}
