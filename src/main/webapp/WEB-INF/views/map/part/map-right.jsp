<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="nmap-right">
    <div class="accordion accordion-flush accordion-dark" id="accordionFlushExample">
        <c:choose>
            <c:when test="${TYPE1 eq 'EVTS'}">
            <div class="accordion-item">
            <h2 class="accordion-header">
                <button class="accordion-button collapsed gap-1" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
                    <span>무인차량목록 - 육군정비창</span>
                    <a class="d-flex">
                        <img src="${pageContext.request.contextPath}/static/images/icon/map-right-reload.svg" width="18" height="18"/>
                    </a>
                </button>
            </h2>
            <div id="flush-collapseOne" class="accordion-collapse collapse show" data-bs-parent="#accordionFlushExample">
                <div class="accordion-body d-flex flex-column gap-2" id="carList"></div>
            </div>
        </div>
            </c:when>
            <c:when test="${TYPE1 eq 'SGVS'}">
            <div class="accordion-item">
            <h2 class="accordion-header">
                <button class="accordion-button collapsed gap-1" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
                    <span>무인 차량 목록 #1</span>
                    <a class="d-flex">
                        <img src="${pageContext.request.contextPath}/static/images/icon/map-right-reload.svg" width="18" height="18"/>
                    </a>
                </button>
            </h2>
            <div id="flush-collapseOne" class="accordion-collapse collapse show" data-bs-parent="#accordionFlushExample">
                <div class="accordion-body d-flex flex-column gap-2">
                    <div class="auto-item success">
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="d-flex flex-column">
                                <span class="item-title">무인 차량 #1</span>
                                <span class="item-info">25/30Km</span>
                            </div>
                            <div class="d-flex align-items-center gap-1">
                                <span class="item-state">정상</span>
                                <a class="d-flex"><img src="${pageContext.request.contextPath}/static/images/icon/map-right-reload.svg"/></a>
                            </div>
                        </div>
                    </div>
                    <div class="auto-item wait">
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="d-flex flex-column">
                                <span class="item-title">무인 차량 #2</span>
                                <span class="item-info">25/30Km</span>
                            </div>
                            <div class="d-flex align-items-center gap-1">
                                <span class="item-state">정상</span>
                                <a class="d-flex"><img src="${pageContext.request.contextPath}/static/images/icon/map-right-reload.svg"/></a>
                            </div>
                        </div>
                    </div>
                    <div class="auto-item error">
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="d-flex flex-column">
                                <span class="item-title">무인 차량 #3</span>
                                <span class="item-info">25/30Km</span>
                            </div>
                            <div class="d-flex align-items-center gap-1">
                                <span class="item-state">정상</span>
                                <a class="d-flex"><img src="${pageContext.request.contextPath}/static/images/icon/map-right-reload.svg"/></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="accordion-item">
            <h2 class="accordion-header">
                <button class="accordion-button collapsed gap-1" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
                    <span>무인 차량 목록 #2</span>
                    <a class="d-flex">
                        <img src="${pageContext.request.contextPath}/static/images/icon/map-right-reload.svg" width="18" height="18"/>
                    </a>
                </button>
            </h2>
            <div id="flush-collapseTwo" class="accordion-collapse collapse" data-bs-parent="#accordionFlushExample">
                <div class="accordion-body d-flex flex-column gap-2">
                    <div class="auto-item success">
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="d-flex flex-column">
                                <span class="item-title">무인 차량 #1</span>
                                <span class="item-info">25/30Km</span>
                            </div>
                            <div class="d-flex align-items-center gap-1">
                                <span class="item-state">정상</span>
                                <a class="d-flex"><img src="${pageContext.request.contextPath}/static/images/icon/map-right-reload.svg"/></a>
                            </div>
                        </div>
                    </div>
                    <div class="auto-item wait">
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="d-flex flex-column">
                                <span class="item-title">무인 차량 #1</span>
                                <span class="item-info">25/30Km</span>
                            </div>
                            <div class="d-flex align-items-center gap-1">
                                <span class="item-state">정상</span>
                                <a class="d-flex"><img src="${pageContext.request.contextPath}/static/images/icon/map-right-reload.svg"/></a>
                            </div>
                        </div>
                    </div>
                    <div class="auto-item error">
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="d-flex flex-column">
                                <span class="item-title">무인 차량 #1</span>
                                <span class="item-info">25/30Km</span>
                            </div>
                            <div class="d-flex align-items-center gap-1">
                                <span class="item-state">정상</span>
                                <a class="d-flex"><img src="${pageContext.request.contextPath}/static/images/icon/map-right-reload.svg"/></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="accordion-item">
            <h2 class="accordion-header">
                <button class="accordion-button collapsed gap-1" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseThree" aria-expanded="false" aria-controls="flush-collapseThree">
                    <span>무인 차량 목록 #3</span>
                    <a class="d-flex">
                        <img src="${pageContext.request.contextPath}/static/images/icon/map-right-reload.svg" width="18" height="18"/>
                    </a>
                </button>
            </h2>
            <div id="flush-collapseThree" class="accordion-collapse collapse" data-bs-parent="#accordionFlushExample">
                <div class="accordion-body d-flex flex-column gap-2">
                    <div class="auto-item success">
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="d-flex flex-column">
                                <span class="item-title">무인 차량 #1</span>
                                <span class="item-info">25/30Km</span>
                            </div>
                            <div class="d-flex align-items-center gap-1">
                                <span class="item-state">정상</span>
                                <a class="d-flex"><img src="${pageContext.request.contextPath}/static/images/icon/map-right-reload.svg"/></a>
                            </div>
                        </div>
                    </div>
                    <div class="auto-item wait">
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="d-flex flex-column">
                                <span class="item-title">무인 차량 #1</span>
                                <span class="item-info">25/30Km</span>
                            </div>
                            <div class="d-flex align-items-center gap-1">
                                <span class="item-state">경고</span>
                                <a class="d-flex"><img src="${pageContext.request.contextPath}/static/images/icon/map-right-reload.svg"/></a>
                            </div>
                        </div>
                    </div>
                    <div class="auto-item error">
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="d-flex flex-column">
                                <span class="item-title">무인 차량 #1</span>
                                <span class="item-info">25/30Km</span>
                            </div>
                            <div class="d-flex align-items-center gap-1">
                                <span class="item-state">주의</span>
                                <a class="d-flex"><img src="${pageContext.request.contextPath}/static/images/icon/map-right-reload.svg"/></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
            </c:when>
        </c:choose>
    </div>
    <%--<div class="container p-2  nmap-right-footer">
        <button type="button" class="btn btn-primary w-100" data-bs-toggle="modal" data-bs-target="#autoRegistModal">
            등록
        </button>
    </div>--%>
</div>
<script>
    function fn_move_by_car(uuid){
        const feature = carSource.getFeatures().find(f => f.get('uuid') === uuid);
        if (!feature) {
            console.warn('해당 ID의 차량을 찾을 수 없습니다:', uuid);
            return;
        }

        const view = N.Map.MAIN_MAP.getView();
        const coords = feature.getGeometry().getCoordinates();

        view.animate({
            center: coords,
            zoom: 17 || view.getZoom(),
            duration: 1000 // 1초 애니메이션
        });
    }
</script>