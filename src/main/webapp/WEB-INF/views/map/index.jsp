<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="map">

</div>
<%@include file="part/map-top.jsp"%>
<%@include file="part/map-left.jsp"%>
<%@include file="part/map-right.jsp"%>
<div class="btn-group nmap-basemap" role="group" aria-label="Basic outlined example">
    <button type="button" class="btn active" data-nmap-name="korean_map">일반지도</button>
    <button type="button" class="btn" data-nmap-name="AIRPHOTO">영상지도</button>
</div>
<%@include file="modal/map-base-modal.jsp"%>
<%@include file="modal/map-view-modal.jsp"%>
<%@include file="modal/map-alert-modal.jsp"%>
<script>
    (function() {
        <c:choose>
        <c:when test="${TYPE1 eq 'EVTS'}">
        N.Map.init("map", [128.682192, 35.280806], function(map){
            addCar('car1',35.280613, 128.681741, 45);
            addCar('car2', 35.281927, 128.683125, 15);
            addCar('car3', 35.282205, 128.675438, 0);
            addCar('car4', 35.284001, 128.676704, 0);
            addCar('car5', 35.279893, 128.678205, 30, 'warning');
            addCar('car6', 35.280138, 128.670631, 20, 'alert');
            map.on('singleclick', function (evt) {
                const feature = map.forEachFeatureAtPixel(evt.pixel, f => f);
                if (feature) {
                    const id = feature.get('id');
                    const modalEl = document.getElementById('autoViewModal');
                    if (!modalEl) return;
                    const modal = bootstrap.Modal.getOrCreateInstance(modalEl, {
                        backdrop: 'static',
                        keyboard: false
                    });
                    modal.show({id:id});
                }
            });
        })
        N.Map.UI.toggleRight()
        </c:when>
        <c:when test="${TYPE1 eq 'SGVS'}">
        N.Map.init("map", [129.425940, 35.974926])
        </c:when>
        </c:choose>
//
        jQuery(".left-menu-list").find(".left-menu-item").on("click", function(){
            jQuery(".left-menu-list").find(".left-menu-item").removeClass("active")
            jQuery(this).addClass("active")
            if(jQuery.trim(jQuery(this).text()) == '무인 차량 목록'){
                var r = jQuery(".nmap-right").css('right')
                var w = jQuery(".nmap-right").width()
                console.log(r)
                if(r !== '0px'){
                    N.Map.UI.toggleRight()
                }
            }
        })


        jQuery('.nmap-basemap').on("click", "button", function () {
            jQuery('.nmap-basemap button').removeClass('active')
            jQuery(this).addClass('active')
            var mapName = jQuery(this).data('nmap-name')
            N.Map.Helper.changeBasemap(mapName)
        })

        // 차량 추가
        function addCar(id, lat, lon, heading = 0, type) {
            var p = ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:5179')
            const feature = new ol.Feature({
                geometry: new ol.geom.Point(p),
                id: id,
                type: type,
                heading: heading
            });
            window.carSource.addFeature(feature);
        }

    })();
    /*document.addEventListener('DOMContentLoaded', function () {
        const modalEl = document.getElementById('autoRegistModal');
        if (!modalEl) return;
        const modal = bootstrap.Modal.getOrCreateInstance(modalEl, {
            backdrop: 'static', // 필요 시: 바깥 클릭 방지
            keyboard: false     // 필요 시: ESC 방지
        });
        //modal.show();
    });*/
</script>
