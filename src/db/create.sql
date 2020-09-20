create table accounts (
  id serial PRIMARY KEY,
  registered boolean,
  login varchar(100),
  password varchar(1000)
);

create table sites (
  id serial PRIMARY KEY,
  host varchar(300),
  account_id int references accounts(id)
);

create table urls (
  id serial PRIMARY KEY,
  url varchar(200),
  key varchar(15),
  total int default 0,
  site_id int references sites(id)
)