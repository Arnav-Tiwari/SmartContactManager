const toggleSidebar=()=>{
 if($(".sidebar").is(":visible")){
   $(".sidebar").css("display","none");
 }
  else{
    $(".sidebar").css("display","block");
}
}