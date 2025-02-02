let date = new Date();
let currentMonth = date.getMonth();
let currentYear = date.getFullYear();

const monthYear = document.getElementById("month-year");
const calendarDates = document.getElementById("calendar-dates");

const months = [
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
];

function generateCalendar(month, year) {
    calendarDates.innerHTML = "";  
    let firstDay = new Date(year, month, 1).getDay();  
    let daysInMonth = new Date(year, month + 1, 0).getDate();  

  
    let adjustedFirstDay = (firstDay === 0) ? 6 : firstDay - 1;

    monthYear.innerHTML = `${months[month]} ${year}`;


    for (let i = 0; i < adjustedFirstDay; i++) {
        const emptyDiv = document.createElement("div");
        emptyDiv.classList.add("empty");  
        calendarDates.appendChild(emptyDiv);
    }

   
    for (let day = 1; day <= daysInMonth; day++) {
        const dayDiv = document.createElement("div");
        dayDiv.innerHTML = day;

        if (
            day === date.getDate() &&
            year === date.getFullYear() &&
            month === date.getMonth()
        ) {
            dayDiv.classList.add("today");
        }

        calendarDates.appendChild(dayDiv);
    }
}

function prevMonth() {
    currentMonth--;
    if (currentMonth < 0) {
        currentMonth = 11;
        currentYear--;
    }
    generateCalendar(currentMonth, currentYear);
}

function nextMonth() {
    currentMonth++;
    if (currentMonth > 11) {
        currentMonth = 0;
        currentYear++;
    }
    generateCalendar(currentMonth, currentYear);
}

generateCalendar(currentMonth, currentYear);