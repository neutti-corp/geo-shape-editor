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
            if(localStorage.getItem('cars') === null){
                addData({lat: 35.280613, lon: 128.681741, heading: 45});
                addData({lat: 35.280613, lon: 128.681741, heading: 45});
                addData({lat: 35.281927, lon: 128.683125, heading: 15});
                addData({lat: 35.282205, lon: 128.675438, heading: 0});
                addData({lat: 35.284001, lon: 128.676704, heading: 0});
                addData({lat: 35.279893, lon: 128.678205, heading: 30, type: 'wait'});
                addData({lat: 35.280138, lon: 128.670631, heading: 20, type: 'error'});
            }
            N.Map.Helper.reloadCars()
            map.on('singleclick', function (evt) {
                const feature = map.forEachFeatureAtPixel(evt.pixel, f => f);
                if (feature) {
                    const uuid = feature.get('uuid');
                    const modalEl = document.getElementById('autoViewModal');
                    if (!modalEl) return;
                    const modal = bootstrap.Modal.getOrCreateInstance(modalEl, {
                        backdrop: 'static',
                        keyboard: false
                    });
                    modal.show({uuid:uuid});
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
        function addData(data) {
            data.uuid = N.Map.Helper.generateUUID()
            const existing = JSON.parse(localStorage.getItem('cars') || '[]');
            existing.push(data);
            localStorage.setItem('cars', JSON.stringify(existing));
        }

        // 데이터 삭제
        function deleteData() {
            const key = document.getElementById("key").value;
            localStorage.removeItem(key);
            alert(`삭제됨: ${key}`);
        }

        // 전체 삭제
        function clearAll() {
            localStorage.clear();
            alert("모든 데이터가 삭제되었습니다.");
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
