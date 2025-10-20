N.Map.Helper = {}
N.Map.Helper.generateUUID = function() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}
N.Map.Helper.changeBasemap = function(tileName){
    var tileNames = [
        "korean_map",
        "color_map",
        "lowV_map",
        "english_map",
        "white_map",
        "chinese_map",
        "japanese_map",
        "white_edu_map",
        "base_hd","satellite_map","night_map", "AIRPHOTO"
    ]
    if(tileNames.indexOf(tileName) > -1){
        var layer = N.Map.Layer.BaseMapLayer
        var current = layer.getSource().layer_
        if(current === tileName){
            // pass
        }else{
            var newSource = N.Map.Layer.BaseMapSource(tileName);
            layer.setSource(newSource);
            layer.getSource().refresh()
        }
    }else{
        console.warn("알수없는 " + tileName + "입니다.")
    }
}
//37.047577, 127.874565
//37.013199, 127.908331
//ol.proj.transformExtent([129.400033,35.956598,129.438228,35.997163],'EPSG:4326','EPSG:5179')
//ol.proj.transformExtent([128.660685,35.275169,128.660685,35.290910],'EPSG:4326','EPSG:5179')
//ol.proj.transformExtent([128.874565,37.013199,128.908331,37.047577],'EPSG:4326','EPSG:5179')
N.Map.Helper.openFullScreen = function(){
    const elem = document.documentElement;
    if (elem.requestFullscreen) {
        elem.requestFullscreen();
    } else if (elem.mozRequestFullScreen) { // Firefox
        elem.mozRequestFullScreen();
    } else if (elem.webkitRequestFullscreen) { // Chrome, Safari
        elem.webkitRequestFullscreen();
    } else if (elem.msRequestFullscreen) { // IE/Edge
        elem.msRequestFullscreen();
    }
}
N.Map.Helper.generateUUID = function() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

N.Map.Helper.reloadCars = function(){
    const cars = JSON.parse(localStorage.getItem('cars') || '[]');
    var html = ""
    for(var i in cars){
        var data = cars[i]
        var p = ol.proj.transform([data.lon, data.lat], 'EPSG:4326', 'EPSG:5179')
        const feature = new ol.Feature({
            geometry: new ol.geom.Point(p),
            uuid: data.uuid,
            type: data.type,
            heading: data.heading
        });
        window.carSource.addFeature(feature);
        //
        html += '<div class="auto-item '+(data.type ? data.type : 'success')+'" onclick="fn_move_by_car(\''+data.uuid+'\')">\n' +
            '                        <div class="d-flex justify-content-between align-items-center">\n' +
            '                            <div class="d-flex flex-column">\n' +
            '                                <span class="item-title">무인 차량 #car'+(+i+1)+'</span>\n' +
            '                                <span class="item-info">10/30Km</span>\n' +
            '                            </div>\n' +
            '                            <div class="d-flex align-items-center gap-1">\n' +
            '                                <span class="item-state">정상</span>\n' +
            '                                <a class="d-flex"><img src="/static/images/icon/map-right-reload.svg"/></a>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                    </div>'
    }
    var target = jQuery("#carList")
    target.html(html)

}