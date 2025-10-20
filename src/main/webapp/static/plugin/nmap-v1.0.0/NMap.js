window.N = {}
N.Map = {
    MAIN_MAP : null
}
N.Map.init = function(elemId, center, callback){
    var map = new ol.Map({
        target: elemId,
        layers: [
            N.Map.Layer.BaseMapLayer
        ],
        view: new ol.View({
            projection: "EPSG:5179",
            center: ol.proj.transform(center, 'EPSG:4326', 'EPSG:5179') ,
            zoom: 15
        })
    });
    map.once('rendercomplete', function (e) {
        var map = this
        // 지도 및 벡터 레이어 구성
        window.carSource = new ol.source.Vector();
        const carLayer = new ol.layer.Vector({
            source: carSource,
            properties: { id: 'carLayer' },
            style: function(feature) {
                const heading = feature.get('heading') || 0;
                const type = feature.get('type')
                return new ol.style.Style({
                    image: new ol.style.Icon({
                        src: "/static/images/icon/car"+(type ? "-" + type : "")+".svg" ,
                        rotation: heading * Math.PI / 180,
                        rotateWithView: true,
                        anchor: [0.5, 0.7],
                        anchorXUnits: 'fraction',
                        anchorYUnits: 'fraction',
                        scale: 0.6
                    })
                });
            }
        });
        map.addLayer(carLayer)
        if(callback){
            callback.call(this, map)
        }
    });
    N.Map.MAIN_MAP = map
}
N.Map.logout = function(){
    if(confirm("로그아웃 하시겠습니까?")){
        Toastify({
            text: "⚠️ 플랫폼 구성중입니다.",
            duration: 4000,
            gravity: "bottom",
            position: "center",
            stopOnFocus: true,
            backgroundColor: "#e77c3c", // 경고 색
            className: "toast-warning",
        }).showToast();
    }
}