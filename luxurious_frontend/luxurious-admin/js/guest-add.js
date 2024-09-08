$(document).ready(function () {

    $('#guestadd-btn').click(async function(){

        // Ngăn hành vi mặc định của form khi click vào button tránh mất giá trị vừa nhập khi click mà giá trị sai
        event.preventDefault();

        var fullName = $('#fullname').val()
        var dob = $('#dob').val()
        var phone = $('#phone').val()
        var email = $('#email').val().toLowerCase()
        var address = $('#address').val()
        var summary = $('#summary').val()

        // Kiểm tra dữ liệu
        var isValid = true;
        var message = "";

        // Kiểm tra fullname (không chứa số hoặc ký tự đặc biệt)
        var nameRegex = /^[a-zA-ZÀ-ỹ\s]+$/;
        if (!fullName.match(nameRegex)) {
            message += "Họ tên không được chứa số hoặc ký tự đặc biệt.\n";
            isValid = false;
        }

        // Kiểm tra phone (chỉ chứa số)
        var phoneRegex = /^\d+$/;
        if (!phone.match(phoneRegex)) {
            message += "Số điện thoại chỉ được chứa số.\n";
            isValid = false;
        }

        // Kiểm tra định dạng dob (yyyy-mm-dd)
        var dobRegex = /^\d{4}-\d{2}-\d{2}$/;
        if (!dob.match(dobRegex)) {
            message += "Định dạng ngày sinh phải là yyyy-mm-dd.\n";
            isValid = false;
        }

        // Kiểm tra định dạng email
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!email.match(emailRegex)) {
            message += "Email chưa đúng định dạng.\n";
            isValid = false;
        }

        try {
            console.log("vohing?")
            var checkEmail = await checkEmailExistence(email);
            if (checkEmail === true) {
                message += "Email đã tồn tại.\n";
                isValid = false;
            }
        } catch (error) {
            alert("Lỗi kiểm tra email: " + error);
            return;
        }

        try {
            var checkPhone = await checkPhoneExistence(phone);
            if (checkPhone === true) {
                message += "Số điện thoại đã tồn tại.\n";
                isValid = false;
            }
        } catch (error) {
            alert("Lỗi kiểm tra số điện thoại: " + error);
            return;
        }

        if (!isValid) {
            alert(message);  // Hiện thông báo lỗi
        } else {
            // Tạo một đối tượng chứa các thông tin từ form (theo đúng cấu trúc của AddGuestRequest)
            var guestData = {
                fullName: fullName,
                dob: dob,
                phone: phone,
                email: email,
                address: address,
                summary: summary
            };
            console.log(JSON.stringify(guestData));
            
            $.ajax({
                url: "http://localhost:9999/user/addguest",
                method: "POST",
                contentType:"application/json",
                data:JSON.stringify(guestData)
            }).done(function(item){
                if (item.statusCode === 200) {
                    alert("Thêm khách thành công!");
                    // Bạn có thể reset form hoặc thực hiện hành động khác
                    $('#fullname').val('');  // Xóa giá trị trong các ô input
                    $('#dob').val('');
                    $('#phone').val('');
                    $('#email').val('');
                    $('#address').val('');
                    $('#summary').val('');
                } else {
                    alert("Đã có lỗi xảy ra, vui lòng thử lại.");
                }
        
            }).fail(function(jqXHR, textStatus, errorThrown) {
                alert("Có lỗi xảy ra: " + textStatus + ": " + errorThrown);
            });
        }

        

    });

    async function checkEmailExistence(email) {
        let checkEmail = false;
        await $.ajax({            
            url: "http://localhost:9999/user/checkemail?email=" + encodeURIComponent(email),
            method: "POST",
            contentType: "application/x-www-form-urlencoded"
        }).done(function(item){
            checkEmail = item.data;
            console.log("tra ve?: "+item);
            console.log(item.data);
        }).fail(function(jqXHR, textStatus, errorThrown) {
            console.log("Lỗi kiểm tra email: " + textStatus + ": " + errorThrown);
        });
        return checkEmail;
    }

    async function checkPhoneExistence(phone) {
        let checkPhone = false;
        await $.ajax({
            url: "http://localhost:9999/user/checkphone?phone=" + encodeURIComponent(phone),
            method: "POST",
            contentType: "application/x-www-form-urlencoded"
        }).done(function(item){
            checkPhone = item.data;
            console.log("Phone exists?: " + item);
            console.log(item.data);
        }).fail(function(jqXHR, textStatus, errorThrown) {
            console.log("Error checking phone: " + textStatus + ": " + errorThrown);
        });
        return checkPhone;
    }

    // Đoạn mã tự động thêm dấu gạch ngang vào ngày sinh
    document.getElementById('dob').addEventListener('input', function(e) {
        // Lấy giá trị hiện tại trong trường input
        let input = e.target.value.replace(/\D/g, '');  // Thay đổi này giúp loại bỏ ký tự không phải số

        // Xóa tất cả các dấu gạch ngang nếu có
        input = input.replace(/-/g, '');

        // Kiểm tra nếu độ dài chuỗi đủ để chèn dấu gạch ngang
        if (input.length > 4 && input.length <= 6) {
            // Chèn dấu gạch ngang giữa năm và tháng
            input = input.slice(0, 4) + '-' + input.slice(4);
        } else if (input.length > 6) {
            // Chèn dấu gạch ngang giữa năm, tháng và ngày
            input = input.slice(0, 4) + '-' + input.slice(4, 6) + '-' + input.slice(6);
        }

        // Cập nhật giá trị của trường input với dấu gạch ngang
        e.target.value = input;
        
    });   

});