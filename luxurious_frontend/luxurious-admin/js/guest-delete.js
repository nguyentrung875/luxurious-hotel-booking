$(document).ready(function () {    
    
    $(document).on('click', '.delete-btn',function(e) { 
        console.log('tới đây ')
        e.preventDefault();

        // Lấy id từ href
        var url = $(this).attr('href'); // Lấy href="#?id={item.id}"
        var id = new URLSearchParams(url.split('?')[1]).get('id'); // Lấy id từ URL

        if (!id) {
            alert('Không tìm thấy ID của khách cần xóa.');
            return;
        }        
        
        $.ajax({
            url: "http://localhost:9999/user/deleteguest",
            method: "POST",
            contentType: "application/x-www-form-urlencoded",
            data: { idGuest: id },
            success: function (response) {
                if (response.data === true) {
                    alert("Khách đã được xóa thành công.");
                    $(e.target).closest('tr').remove(); // Xóa dòng bảng hiện tại nếu cần
                } else {
                    alert("Có lỗi xảy ra, vui lòng thử lại.");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("Error deleting guest: " + textStatus + ": " + errorThrown);
                alert("Xóa khách không thành công.");
            }
        });
        
    });
});