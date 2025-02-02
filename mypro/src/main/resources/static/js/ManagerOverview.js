function toggleDropdown(){
    const dropdown=document.getElementById('dropdown');
    if (dropdown.style.display==='block'){
        dropdown.style.display='none';
    }else{
        dropdown.style.display='block';
    }
}
function side(){
    const chatSide=document.getElementById("chat-side");
    if (chatSide.style.width==="30%"){
        chatSide.style.width=0;
    }else{
        chatSide.style.width="30%";
    }
}
function show(id){
    const divs=document.querySelectorAll('.indivchat');
    divs.forEach(div=>div.classList.remove('active'));
    const mydiv=document.getElementById(id);
    if(mydiv){
        mydiv.classList.add('active');
    }
}
function exit(){
    const divs=document.querySelectorAll('.indivchat');
    divs.forEach(div=>div.classList.remove('active'));
}
function assign(){
    const assign=document.getElementById('AssignList');
    if (assign.style.display==='block'){
        assign.style.display='none';
    }else{
        assign.style.display='block';
    }
}
function post(){
    const post=document.getElementById('PostList');
    if (post.style.display==='block'){
        post.style.display='none';
    }else{
        post.style.display='block';
    }
}
