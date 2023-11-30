package A23.C6.TP3.ServiceREST.PMAF.log;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Loggeur implements HandlerInterceptor {
    private String adresseIp;
    private boolean ok = false;

    private void ecrirelog() {

        FileWriter fileWriter = null;
        try {
            File f = ResourceUtils.getFile("adresseip.log");

            fileWriter = new FileWriter(f, true);
            fileWriter.append(this.adresseIp);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals("POST")) {
            adresseIp = "";

            if (request != null) {
                adresseIp = request.getHeader(("X-FORWARDED-FOR"));
                if (adresseIp == null || "".equals(adresseIp)) {
                    adresseIp = request.getRemoteAddr();
                    ok = true;
                }
            }
        }
        return ok;
    }
}
