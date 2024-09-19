$(document).ready(function(){
    var searchParam = new URLSearchParams(window.location.search)
    var id= searchParam.get("id")
     $.ajax({
            url: `http://localhost:9999/employee/${id}`,
            method: "GET",
        }).done(function( data ) {
              console.log(data)
              if(data.statusCode==200){

              }else{
                
              }
        });
})