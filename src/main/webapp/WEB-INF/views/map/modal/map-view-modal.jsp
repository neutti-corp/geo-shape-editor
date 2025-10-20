<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal fade cmmn-modal" id="autoViewModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">무인차량확인</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="d-flex flex-column p-2 gap-2">
                    <div class="default-input-group">
                        <label>차량명</label>
                        <input name="carId" value="무인 차량 #1" disabled/>
                    </div>
                    <div class="default-input-group">
                        <label>차량번호</label>
                        <input value="20 대 4256"/>
                    </div>
                    <div class="default-input-group">
                        <label>모델</label>
                        <input value="K-5"/>
                    </div>
                    <div class="default-input-group">
                        <label>Secret Key</label>
                        <input value="K25HIKJFDL"/>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="custom-btn orange" data-bs-dismiss="modal">수정</button>
                <button type="button" class="custom-btn red" onclick="fn_remove_car()">삭제</button>
            </div>
        </div>
    </div>
</div>
<script>
    (function(){
        const modal = document.getElementById('autoViewModal');
        modal.addEventListener('show.bs.modal', event => {
            const param = event.relatedTarget; // 클릭한 버튼
            jQuery(modal).find("[name='carId']").val("무인 차량 #" + param.id)
            window.___modal_param = param
            console.log(this, event)
        });
    })();
    function fn_remove_car(){
        if(confirm("등록차량을 삭제 하시겠습니까?")){
            const feature = carSource.getFeatures().find(f => f.get('id') === window.___modal_param.id);
            if (feature) carSource.removeFeature(feature);
        }
        $('#autoViewModal').modal('hide');
    }
</script>