package com.neutti.helper;

import com.neutti.MapApplication;
import com.neutti.vo.TileVO;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TileMapScanner {
    private static final String[] LAYERS = {"AIRPHOTO","korean_map"};
    /* 전체영역 */
    private static final double[] EXTENT = {-200000.0, -3015.4524155292, 3803015.45241553, 4000000.0};
    private static final double[] ORIGIN = {-200000.0, 4000000.0};
    //private static final double[] VALID_EXTENT = {831840.1591541814, 1446910.847648412, 1233569.9630996522, 2080637.712895636};
    //private static final double[] VALID_EXTENT = {701005.6341562872, 1450022.2611490092, 1392490.4740230169, 2086050.5542832382};
    /**
     * 해명대교육훈련단
     */
    //private static final double[] VALID_EXTENT = {1171261, 1774988, 1174793, 1779555};
    /**
     * 육군정비창
     */
    //14318864.798968697, 4197673.570669665, 14329833.910867611, 4207570.04278974
    //1101823.2731097527 //1694206.6643801897 //1111410.0164777935 //1702855.946048429
    private static final double[] VALID_EXTENT = {1101823, 1694206, 1111410, 1702855};
    /**
     * 전투비행단
     */
    //private static final double[] VALID_EXTENT = {1122230, 1891404, 1125289, 1895262};
    private static final int TILE_SIZE = 256;
    private static final String[] MATRIX_IDS = {"L05", "L06", "L07", "L08", "L09", "L10", "L11", "L12", "L13", "L14", "L15", "L16", "L17", "L18", "L19"};
    private static final String[] MATRIX_IDS2 = {"5","6","7","8","9","10","11","12","13","14","15","16","17","18","19"};
    private static final double[] RESOLUTIONS = {2088.96, 1044.48, 522.24, 261.12, 130.56, 65.28, 32.64, 16.32, 8.16, 4.08, 2.04, 1.02, 0.51, 0.255};
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
    public List<TileVO> scanTilesForZoom(int zoom) {
        List<TileVO> tileList = new ArrayList<>();
        if (zoom < 5 || zoom >= 19) {
            System.out.println("대상타일없음");
            return tileList;
        }
        double resolution = RESOLUTIONS[zoom - 5];
        String matrixId = MATRIX_IDS[zoom - 5];
        String matrixId2 = MATRIX_IDS2[zoom - 5];
        //int gridWidth = (int) Math.ceil((EXTENT[2] - EXTENT[0]) / (TILE_SIZE * resolution));
        //int gridHeight = (int) Math.ceil((EXTENT[3] - EXTENT[1]) / (TILE_SIZE * resolution));
        int minX = (int) Math.floor((VALID_EXTENT[0] - ORIGIN[0]) / (TILE_SIZE * resolution));
        int minY = (int) Math.floor((ORIGIN[1] - VALID_EXTENT[3]) / (TILE_SIZE * resolution));
        int maxX = (int) Math.ceil((VALID_EXTENT[2] - ORIGIN[0]) / (TILE_SIZE * resolution));
        int maxY = (int) Math.ceil((ORIGIN[1] - VALID_EXTENT[1]) / (TILE_SIZE * resolution));
        int tileCount = (maxX - minX + 1) * (maxY - minY + 1);
        System.out.println(zoom + " 레벨 타일 개수: " + tileCount);
        System.out.println(zoom + " 레벨 타일 시간: " + (tileCount * .8));
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                double tileMinX = ORIGIN[0] + x * TILE_SIZE * resolution;
                double tileMaxY = ORIGIN[1] - y * TILE_SIZE * resolution;
                double tileMaxX = tileMinX + TILE_SIZE * resolution;
                double tileMinY = tileMaxY - TILE_SIZE * resolution;
                if (tileMinX < EXTENT[2] && tileMaxX > EXTENT[0] &&
                        tileMinY < EXTENT[3] && tileMaxY > EXTENT[1]) {
                    tileList.add(new TileVO(matrixId, matrixId2, x, y, tileMinX, tileMinY, tileMaxX, tileMaxY));
                }
            }
        }

        return tileList;
    }

    private void execute(int startZoom, int endZoom) throws IOException {
        for(int zoom = startZoom; zoom <= endZoom; zoom++){
            List<TileVO> tiles = scanTilesForZoom(zoom);
            for (TileVO tile : tiles) {
                for (String layer : LAYERS) {
                    String _mapUrl;
                    String style = "korean";
                    String format = "image/png";
                    String matrixSet = "korean";
                    String matrixId = tile.getMatrixId();
                    if(layer.equals("AIRPHOTO")){
                        _mapUrl = mapSubUrl;
                        matrixSet = "NGIS_AIR";
                        style = "_null";
                        format = "image/jpg";
                        matrixId = tile.getMatrixId2();
                    }else{
                        _mapUrl = mapUrl;
                    }
                    mapApikey = "04trYP9_xwLAfALjwZ-B8g";
                    String urlStr = _mapUrl + "?apikey=" + URLEncoder.encode(mapApikey,"utf-8");
                    urlStr += "&layer=" + layer;
                    urlStr += "&style=" + style;
                    urlStr += "&tilematrixset=" + matrixSet;
                    urlStr += "&Service=WMTS";
                    urlStr += "&Request=GetTile";
                    urlStr += "&Version=1.0.0";
                    urlStr += "&Format=" + URLEncoder.encode(format,"utf-8");
                    urlStr += "&TileMatrix=" + matrixId;
                    urlStr += "&TileCol=" + tile.getX();
                    urlStr += "&TileRow=" + tile.getY();
                    //
                    String path = getCachePath() + "ngii/" + layer + "/" + matrixId + "/" + tile.getX() + "_" + tile.getY();
                    if(format.equals("image/jpg")){
                        path +=  ".jpg";
                    }else {
                        path +=  ".png";
                    }
                    File file = new File(path);
                    //
                    if(!file.exists()){
                        System.out.println(zoom + " " + tile + " " + layer + ": " + urlStr);
                        file.getParentFile().mkdirs();
                        HttpURLConnection huc = null;
                        try{
                            //URL url = new URL("https://map.ngii.go.kr/openapi/Gettile.do?apikey=04trYP9_xwLAfALjwZ-B8g&layer=korean_map&style=korean&tilematrixset=korean&Service=WMTS&Request=GetTile&Version=1.0.0&Format=image%2Fpng&TileMatrix=L07&TileCol=10&TileRow=14");
                            URL url = new URL(urlStr.replaceAll(" ", "%20"));
                            URLConnection connection = url.openConnection();
                            huc = (HttpURLConnection)connection;
                            huc.setConnectTimeout(1000);
                            huc.setRequestMethod("GET");
                            huc.setRequestProperty("Referer", "http://localhost:8080");
                            huc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
                            huc.setDoOutput(true);
                            huc.setDoInput(true);
                            huc.setUseCaches(false);
                            huc.setDefaultUseCaches(false);
                            if(huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                int contentLength = connection.getContentLength();
                                if(true){
                                    //if(contentLength > 0){
                                    OutputStream out = Files.newOutputStream(file.toPath());
                                    IOUtils.copy(huc.getInputStream(), out);
                                    out.close();
                                }else{
                                    // EMPTY IMAGE 대체
                                    File emptyFile = null;
                                    if(format.equals("image/jpg")){
                                        emptyFile = ResourceUtils.getFile("classpath:data/images/empty_image.jpg");
                                    }else if(format.equals("image/png")){
                                        emptyFile = ResourceUtils.getFile("classpath:data/images/empty_image.png");
                                    }
                                    if(emptyFile != null){
                                        System.out.println(">>>>>>> EMPTY IMAGE - " + file.toPath());
                                        InputStream in = Files.newInputStream(emptyFile.toPath());
                                        OutputStream out = Files.newOutputStream(file.toPath());
                                        IOUtils.copy(in, out);
                                        in.close();
                                        out.close();
                                    }
                                }
                            }else {
                                IOUtils.copy(huc.getInputStream(), System.out);
                            }
                        }catch(IOException e) {
                            e.printStackTrace();
                        } finally {
                            if(huc != null){
                                huc.disconnect();
                            }
                            /*try {
                                //int randomNumber = ThreadLocalRandom.current().nextInt(10, 50);
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }*/
                        }
                    }else{
                        System.out.println(zoom + " " + tile + " " + layer + ": EXIST!!!");
                    }
                }
            }
        }
    }
    public String getCachePath() {
        String absolutePath = Paths.get(cachePath.replaceFirst("^~", System.getProperty("user.home").replaceAll("\\\\","/"))).toAbsolutePath().toString();
        if(!absolutePath.endsWith("/")){
            absolutePath = absolutePath + "/";
        }
        return absolutePath;
    }
    public static void main(String[] args) throws IOException {
        Date start = new Date();
        System.out.println(">>>>>>> START : " + start);
        SpringApplication app = new SpringApplication(MapApplication.class);
        app.setAdditionalProfiles("outside");
        ApplicationContext context = app.run(args);
        TileMapScanner scanner = context.getBean(TileMapScanner.class);
        scanner.execute(0,18);
        //
        Date end = new Date();
        System.out.println(">>>>>>> END : " + end);
        System.out.println(">>>>>>> 처리시간 : " + ((end.getTime() - start.getTime()) / 1000) + "초");
        System.exit(-1);
    }

}
