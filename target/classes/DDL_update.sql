CREATE SEQUENCE companies_id_seq;
ALTER TABLE public.companies ALTER COLUMN id SET DEFAULT nextval('companies_id_seq');
ALTER SEQUENCE companies_id_seq OWNED BY public.companies.id;
SELECT setval('companies_id_seq', (SELECT max(id) FROM public.companies));

CREATE SEQUENCE developers_id_seq;
ALTER TABLE public.developers ALTER COLUMN id SET DEFAULT nextval('developers_id_seq');
ALTER SEQUENCE developers_id_seq OWNED BY public.developers.id;
SELECT setval('developers_id_seq', (SELECT max(id) FROM public.developers));

CREATE SEQUENCE skills_id_seq;
ALTER TABLE public.skills ALTER COLUMN id SET DEFAULT nextval('skills_id_seq');
ALTER SEQUENCE skills_id_seq OWNED BY public.skills.id;
SELECT setval('skills_id_seq', (SELECT max(id) FROM public.skills));

CREATE SEQUENCE projects_id_seq;
ALTER TABLE public.projects ALTER COLUMN id SET DEFAULT nextval('projects_id_seq');
ALTER SEQUENCE projects_id_seq OWNED BY public.projects.id;
SELECT setval('projects_id_seq', (SELECT max(id) FROM public.projects));

CREATE SEQUENCE customers_id_seq;
ALTER TABLE public.customers ALTER COLUMN id SET DEFAULT nextval('customers_id_seq');
ALTER SEQUENCE customers_id_seq OWNED BY public.customers.id;
SELECT setval('customers_id_seq', (SELECT max(id) FROM public.customers));



ALTER TABLE developers_projects DROP CONSTRAINT developers_projects_developer_id_fkey;
ALTER TABLE developers_projects DROP CONSTRAINT developers_projects_project_id_fkey;

ALTER TABLE developers_projects
ADD CONSTRAINT dev_proj_id_const
foreign key (developer_id) references developers(id) ON DELETE CASCADE;

ALTER TABLE developers_projects
ADD CONSTRAINT proj_dev_id_const
foreign key (project_id) references projects(id) ON DELETE CASCADE;


ALTER TABLE developers_skills DROP CONSTRAINTS developers_skills_developer_id_fkey;
ALTER TABLE developers_skills DROP CONSTRAINTS developers_skills_skill_id_fkey;

ALTER TABLE developers_skills
ADD CONSTRAINT dev_skill_id_const
foreign key (developer_id) references developers(id) ON DELETE CASCADE;

ALTER TABLE developers_skills
ADD CONSTRAINT skill_dev_id_const
foreign key (skill_id) references skills(id) ON DELETE CASCADE;

ALTER TABLE customers_projects DROP CONSTRAINT customers_projects_project_id_fkey;
ALTER TABLE customers_projects DROP CONSTRAINT customers_projects_customer_id_fkey;

ALTER TABLE customers_projects
ADD CONSTRAINT cus_proj_id_const
foreign key (customer_id) references customers(id) ON DELETE CASCADE;

ALTER TABLE customers_projects
ADD CONSTRAINT proj_cus_id_const
foreign key (project_id) references projects(id) ON DELETE CASCADE;

ALTER TABLE companies_projects DROP CONSTRAINT companies_projects_project_id_fkey;
ALTER TABLE companies_projects DROP CONSTRAINT companies_projects_company_id_fkey;

ALTER TABLE companies_projects
ADD CONSTRAINT com_proj_id_const
foreign key (company_id) references companies(id) ON DELETE CASCADE;

ALTER TABLE companies_projects
ADD CONSTRAINT proj_com_id_const
foreign key (project_id) references projects(id) ON DELETE CASCADE;

ALTER TABLE public.developers
ADD salary INT;

ALTER TABLE public.projects
ADD start_date DATE;