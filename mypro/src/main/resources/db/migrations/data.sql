create table staff(
	person_id serial primary key,
	name varchar(200) not null,
	username varchar(50) unique,
	password varchar(100) not null,
	email varchar(100) not null unique
	);

create table tasks(
	task_id serial primary key,
	title varchar(200) not null,
	describtion text,
	assigned_to int references staff(person_id),
	dueDate date not null,
	status varchar(50),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	completed_on date
);

create table chats(
	chat_id serial primary key,
	person1 int references staff(person_id),
	person2 int references staff(person_id),
	UNIQUE(person1, person2)
);
create table messages (
    message_id serial primary key,
    chat_id int references chats(chat_id),  
    sender_id int references staff(person_id),  
    receiver_id int references staff(person_id),
    message text not null,  
    created_at timestamp default current_timestamp
);
create table notifications (
    notification_id serial primary key,
    task_id int references tasks(task_id), 
    show_to int references staff(person_id),
    status varchar(20) default 'Unread',
    created_at timestamp default current_timestamp
);