<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal fade cmmn-modal" id="autoRegistModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">무인차량등록</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form class="modal-body" id="autoRegistModalForm">
                <div class="d-flex flex-column p-2 gap-2">
                    <div class="default-input-group">
                        <label>차량명</label>
                        <input name="name" value="무인 차량 #1"/>
                    </div>
                    <div class="default-input-group">
                        <label>차량번호</label>
                        <input name="num" value="20 대 4256" />
                    </div>
                    <div class="default-input-group">
                        <label>모델</label>
                        <input name="model" value="K-5" />
                    </div>
                    <div class="default-input-group">
                        <label>Secret Key</label>
                        <input name="key" value="K25HIKJFDL"/>
                    </div>
                </div>
            </form>
            <div class="modal-footer">
                <button type="button" class="custom-btn green" onclick="fn_add_car()">등록</button>
            </div>
        </div>
    </div>
</div>
<script>
    function fn_add_car(){
        if(confirm("차량을 등록 하시겠습니까?")){
            var lon = Math.random() * (128.68 - 128.66) + 128.66;
            var lat = Math.random() * (35.29 - 35.28) + 35.28;
            var heading = Math.random() * (90 - 0) + 0;
            addData({lat: lat, lon: lon, heading: heading});
            N.Map.Helper.reloadCars()
            $('#autoRegistModal').modal('hide');
        }
    }
    // 차량 추가
    function addData(data) {
        data.uuid = N.Map.Helper.generateUUID()
        const existing = JSON.parse(localStorage.getItem('cars') || '[]');
        existing.push(data);
        localStorage.setItem('cars', JSON.stringify(existing));
    }
</script>

