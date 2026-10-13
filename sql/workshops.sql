CREATE TABLE IF NOT EXISTS workshops(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL ,
    description TEXT NOT NULL ,
    work_date TEXT NOT NULL ,
    capacity INTEGER NOT NULL CHECK ( capacity > 0 )
);