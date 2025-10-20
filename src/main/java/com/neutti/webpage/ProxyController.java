package com.neutti.webpage;

import com.neutti.helper.MapEnvHelper;
import com.neutti.vo.WmsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;


@Slf4j
@RestController
@RequestMapping("proxy")
public class ProxyController {

    @Resource
    public MapEnvHelper envHelper;
    @Value("${application.map.ngii.cache.path}")
    private String cachePath;
    @Value("${application.map.ngii.apikey}")
    private String mapApikey;
    @Value("${application.map.ngii.sub.url}")
    private String mapSubUrl;
    @Value("${application.map.ngii.url}")
    private String mapUrl;
    @Value("${application.map.geoserver.url}")
    private String geoserverUrl;
    @RequestMapping(value="/basemap.do")
    public void basemap(@ModelAttribute WmsVO param, HttpServletResponse response)
            throws IOException, InterruptedException {
        String layer = param.getLayer();
        String tileMatrix = param.getTileMatrix();
        String tileCol = param.getTileCol();
        String tileRow = param.getTileRow();
        String path = getCachePath() + "ngii/" + layer + "/" + tileMatrix + "/" + tileCol + "_" + tileRow;
        if(param.getFormat().equals("image/jpg")){
            path +=  ".jpg";
        }else {
            path +=  ".png";
        }
        response.setContentType(param.getFormat());
        File file = new File(path);
        if(envHelper.isDocker()){
            // URL 호출 안함
            if(!file.exists()) {
                System.out.println("Image Not Exist :: " + path);
                // EMPTY IMAGE 대체
                File emptyFile = null;
                if (param.getFormat().equals("image/jpg")) {
                    emptyFile = ResourceUtils.getFile("classpath:data/images/empty_image.jpg");
                } else if (param.getFormat().equals("image/png")) {
                    emptyFile = ResourceUtils.getFile("classpath:data/images/empty_image.png");
                }
                if (emptyFile != null) {
                    file = emptyFile;
                }
            }
        }else{;
            if(!file.exists()){
                // 지도 서버 shutdown... 대응
                int randomNumber = ThreadLocalRandom.current().nextInt(100, 500);
                Thread.sleep(randomNumber);
                //
                file.getParentFile().mkdirs();
                // MAPTI, MAPFI
                String _mapUrl;
                if(param.getLayer().equals("AIRPHOTO")){
                    _mapUrl = mapSubUrl;
                }else{
                    _mapUrl = mapUrl;
                }
                String urlStr = _mapUrl + "?apikey=" + URLEncoder.encode(mapApikey,"utf-8");
                urlStr += "&layer=" + param.getLayer();
                urlStr += "&style=" + param.getStyle();
                urlStr += "&tilematrixset=" + param.getTilematrixset();
                urlStr += "&Service=" + param.getService();
                urlStr += "&Request=" + param.getRequest();
                urlStr += "&Version=" + param.getVersion();
                urlStr += "&Format=" + URLEncoder.encode(param.getFormat(),"utf-8");
                urlStr += "&TileMatrix=" + param.getTileMatrix();
                urlStr += "&TileCol=" + param.getTileCol();
                urlStr += "&TileRow=" + param.getTileRow();
                //
                System.out.println("Image Not Exist :: " + path);
                //
                HttpURLConnection huc = null;
                OutputStream ios = null;
                try{
                    URL url = new URL(urlStr.replaceAll(" ", "%20"));
                    URLConnection connection = url.openConnection();
                    huc = (HttpURLConnection)connection;
                    huc.setConnectTimeout(2000);
                    huc.setRequestMethod("GET");
                    huc.setRequestProperty("Referer", "http://localhost:8080");
                    huc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
                    huc.setDoOutput(true);
                    huc.setDoInput(true);
                    huc.setUseCaches(false);
                    huc.setDefaultUseCaches(false);
                    response.reset();
                    response.setContentType(huc.getContentType());
                    if(huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        // 1일 웹캐쉬 설정
                        response.setHeader("Cache-Control", "public, max-age=86400");
                        response.setHeader("Pragma", "cache");
                        response.setDateHeader("Expires", System.currentTimeMillis() + 86400000L);
                        response.setContentLength(huc.getContentLength());
                        IOUtils.copy(huc.getInputStream(), Files.newOutputStream(file.toPath()));
                    }else {
                        IOUtils.copy(huc.getInputStream(), System.out);
                    }
                }catch(IOException e) {
                    if(!response.isCommitted()) {
                        //response.sendError(200, e.getMessage());
                        response.setContentType("text/plain;charset=UTF-8");
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getWriter().write("Error");
                    }
                }
                finally {
                    if(huc != null){
                        huc.disconnect();
                    }
                    if(ios != null){
                        ios.close();
                    }
                }
            }
        }
        // 1일 웹캐쉬 설정
        response.setHeader("Cache-Control", "public, max-age=86400");
        response.setHeader("Pragma", "cache");
        response.setDateHeader("Expires", System.currentTimeMillis() + 86400000L);
        InputStream in = null;
        try{
            in = Files.newInputStream(file.toPath());
            IOUtils.copy(in, response.getOutputStream());
        }finally {
            if(in != null) in.close();
        }
    }

    public String getCachePath() {
        String absolutePath = Paths.get(cachePath.replaceFirst("^~", System.getProperty("user.home").replaceAll("\\\\","/"))).toAbsolutePath().toString();
        if(!absolutePath.endsWith("/")){
            absolutePath = absolutePath + "/";
        }
        return absolutePath;
    }

    /**
     * 내부 Geoserver Proxy
     * @param response
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping(value="/geoserver-{layer}.do")
    public void geoserver(@PathVariable String layer, HttpServletRequest request, HttpServletResponse response)
            throws IOException, InterruptedException {
        // http://220.90.239.149:8080/geoserver/wastAll/wms?REQUEST=GetMap&SERVICE=WMS&VERSION=1.1.1&FORMAT=image%2Fpng&STYLES=&TRANSPARENT=true&LAYERS=wastAll%3Awest-all-5179&TILED=true&FORMAT_OPTIONS=dpi%3A180&WIDTH=512&HEIGHT=512&SRS=EPSG%3A5179&BBOX=977298.699767705%2C1915505.451544702%2C1016390.6477584133%2C1954597.3995354103
        response.setContentType("image/png");
        String urlStr = geoserverUrl + "/" + layer + "/wms?" + request.getQueryString();
        //
        HttpURLConnection huc = null;
        OutputStream ios = null;
        try{
            URL url = new URL(urlStr.replaceAll(" ", "%20"));
            URLConnection connection = url.openConnection();
            huc = (HttpURLConnection)connection;
            huc.setConnectTimeout(2000);
            huc.setRequestMethod("GET");
            huc.setRequestProperty("Referer", "http://localhost:8080");
            huc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
            huc.setDoOutput(true);
            huc.setDoInput(true);
            huc.setUseCaches(false);
            huc.setDefaultUseCaches(false);
            response.reset();
            response.setContentType(huc.getContentType());
            if(huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 1일 웹캐쉬 설정
                response.setHeader("Cache-Control", "public, max-age=86400");
                response.setHeader("Pragma", "cache");
                response.setDateHeader("Expires", System.currentTimeMillis() + 86400000L);
                response.setContentLength(huc.getContentLength());
                IOUtils.copy(huc.getInputStream(), response.getOutputStream());
            }else {
                IOUtils.copy(huc.getInputStream(), System.out);
            }
        }catch(IOException e) {
            if(!response.isCommitted()) {
                //response.sendError(200, e.getMessage());
                response.setContentType("text/plain;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Error");
            }
        }
        finally {
            if(huc != null){
                huc.disconnect();
            }
            if(ios != null){
                ios.close();
            }
        }
    }
}
