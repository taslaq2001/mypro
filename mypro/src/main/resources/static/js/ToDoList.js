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
function Add(){
    const addt=document.getElementById('AddList');
    if (addt.style.display==='block'){
        addt.style.display='none';
    }else{
        addt.style.display='block';
    }
}