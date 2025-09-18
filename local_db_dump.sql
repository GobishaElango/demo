--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    product_id uuid DEFAULT gen_random_uuid() NOT NULL,
    isactive character varying(1) DEFAULT 'Y'::character varying,
    created timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    createdby character varying(255) DEFAULT '1'::character varying,
    updated timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updatedby character varying(255) DEFAULT '1'::character varying,
    name character varying(255) NOT NULL,
    value character varying(32) NOT NULL,
    uom character varying(50) NOT NULL
);


ALTER TABLE public.product OWNER TO postgres;

--
-- Name: purchaseorder; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.purchaseorder (
    order_id uuid DEFAULT gen_random_uuid() NOT NULL,
    isactive character varying(1) DEFAULT 'Y'::character varying,
    created timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    createdby character varying(255) DEFAULT '1'::character varying,
    updated timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updatedby character varying(255) DEFAULT '1'::character varying,
    document_no character varying(255) NOT NULL,
    order_date date NOT NULL,
    delivery_date date NOT NULL,
    vendor_id uuid NOT NULL,
    vendor_address character varying(500) NOT NULL,
    delivery_address character varying(500) NOT NULL,
    total_amount numeric(12,2)
);


ALTER TABLE public.purchaseorder OWNER TO postgres;

--
-- Name: purchaseorderline; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.purchaseorderline (
    orderline_id uuid DEFAULT gen_random_uuid() NOT NULL,
    isactive character varying(1) DEFAULT 'Y'::character varying,
    created timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    createdby character varying(255) DEFAULT '1'::character varying,
    updated timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updatedby character varying(255) DEFAULT '1'::character varying,
    order_id uuid NOT NULL,
    product_id uuid NOT NULL,
    price numeric(10,2) NOT NULL,
    quantity integer NOT NULL,
    amount numeric(12,2) NOT NULL,
    uom character varying(32) NOT NULL
);


ALTER TABLE public.purchaseorderline OWNER TO postgres;

--
-- Name: userinfo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.userinfo (
    userinfo_id character varying(32) NOT NULL,
    isactive character varying(1) DEFAULT 'Y'::character varying,
    created timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    createdby character varying(255),
    updated timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updatedby character varying(255),
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.userinfo OWNER TO postgres;

--
-- Name: vendor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vendor (
    vendor_id uuid DEFAULT gen_random_uuid() NOT NULL,
    isactive character varying(1) DEFAULT 'Y'::character varying,
    created timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    createdby character varying(255) DEFAULT '1'::character varying,
    updated timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updatedby character varying(255) DEFAULT '1'::character varying,
    name character varying(255) NOT NULL,
    value character varying(32) NOT NULL,
    address text NOT NULL
);


ALTER TABLE public.vendor OWNER TO postgres;

--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product (product_id, isactive, created, createdby, updated, updatedby, name, value, uom) FROM stdin;
651f0b2e-fd59-4ad3-b43f-32568e9eb83c	Y	2025-03-15 12:05:19.715884	1	2025-03-15 12:05:19.715884	1	Cucumber	PROD001	Kg
2bff7c6b-e846-4671-8a54-fb7d81435a88	Y	2025-03-15 12:05:19.715884	1	2025-03-15 12:05:19.715884	1	Onion	PROD002	Kg
1a007919-65f3-481f-9f8b-26381d72be10	Y	2025-03-15 12:05:19.715884	1	2025-03-15 12:05:19.715884	1	Aachi Chilli Powder - 100g	PROD003	pcs
3c3dbfce-c45c-4f91-9052-0dc81358cbb6	Y	2025-03-15 12:05:19.715884	1	2025-03-15 12:05:19.715884	1	Gold Winner Refined Sunflower Oil - 1L	PROD004	pcs
1091d3f2-aa71-467b-9c17-39082e550677	Y	2025-03-15 12:05:19.715884	1	2025-03-15 12:05:19.715884	1	Tata Urad Dal - 1Kg	PROD005	Kg
8e1d0043-4e53-4e0f-a0dd-b9b851d5c56e	Y	2025-03-15 12:05:19.715884	1	2025-03-15 12:05:19.715884	1	Orange	PROD006	Kg
1e76fe71-852c-4213-a9af-036266bf67c1	Y	2025-03-15 12:05:19.715884	1	2025-03-15 12:05:19.715884	1	Rice - 1Kg	PROD007	Kg
7642c43e-77a4-4b58-b03e-b3033f1d947c	Y	2025-03-15 12:05:19.715884	1	2025-03-15 12:05:19.715884	1	Bru Instant Coffee - 200g	PROD008	pcs
8342a039-f732-42f0-aabc-bab0562e5086	Y	2025-03-15 12:05:19.715884	1	2025-03-15 12:05:19.715884	1	Maggi Noodles-150g	PROD009	pcs
\.


--
-- Data for Name: purchaseorder; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.purchaseorder (order_id, isactive, created, createdby, updated, updatedby, document_no, order_date, delivery_date, vendor_id, vendor_address, delivery_address, total_amount) FROM stdin;
5cba6c89-66d8-4901-a443-647463a02f5e	Y	2025-09-04 12:37:44.664458	1	2025-09-04 12:37:44.664458	1	PO011	2025-09-04	2025-09-18	e797606c-3908-4c79-8f5a-75f327aa62f0	Shop No. 5, Luz Corner, Mylapore, Chennai - 600004	No. 15, 5th Main Road, Besant Nagar, Chennai -  600090	1050.00
c1763462-9684-4588-aaf7-6719763133ab	Y	2025-09-04 12:39:57.125619	1	2025-09-04 12:39:57.125619	1	PO013	2025-09-04	2025-09-10	e797606c-3908-4c79-8f5a-75f327aa62f0	45, North Usman Road, T. Nagar, Chennai - 600017	Plot No. 77, 3rd Cross Street, Neelankarai, Chennai - 600115	1440.00
b20db870-eebd-4bd3-8a51-490c6dc1c0ae	Y	2025-09-04 12:41:31.899384	1	2025-09-04 12:41:31.899384	1	PO014	2025-09-04	2025-09-24	370d2489-da21-403e-bfd6-34ded5e4f3ac	No. 12, Anna Salai, Teynampet, Chennai - 600018	3-I, Krishna Accolade, Jayachandran Nagar, 2nd street, Medavakkam - 600096	1778.00
de9273d5-b8e9-4241-b309-18011675b0e6	Y	2025-09-04 12:42:26.017494	1	2025-09-04 12:42:26.017494	1	PO015	2025-09-04	2025-09-25	f93f4689-e5ff-4010-93b5-02c284466be6	58, Anna Salai, Teynampet, Chennai - 600018	Door No. 23, 2nd Avenue, Anna Nagar, Chennai - 600040	1218.00
4a9349a7-e6fd-4e3c-9e08-69de713787b3	Y	2025-09-05 13:06:44.080524	1	2025-09-05 13:06:44.080524	1	PO025	2025-09-05	2025-09-18	370d2489-da21-403e-bfd6-34ded5e4f3ac	58, Anna Salai, Teynampet, Chennai - 600018	No. 15, 5th Main Road, Besant Nagar, Chennai - 600090	1393.00
81867946-0eb5-49ce-8b27-636d8a3b97eb	Y	2025-09-04 14:29:56.295918	1	2025-09-04 14:29:56.295918	1	PO019	2025-09-04	2025-09-18	831afbb3-5f7a-4232-b7ed-136ced9184da	76, 100 Feet Road, Vadapalani, Chennai - 600026	45, Mount Road, Guindy, Chennai - 600032	1462.00
aa660b24-59b3-4fd9-b3d2-d9b08cc3e9b2	Y	2025-09-04 14:33:33.331224	1	2025-09-04 14:33:33.331224	1	PO022	2025-09-04	2025-09-25	370d2489-da21-403e-bfd6-34ded5e4f3ac	No. 15, 5th Main Road, Besant Nagar, Chennai - 600090	23, Velachery Main Road, Velachery, Chennai - 600042	2407.00
396617ed-3405-42ee-8ff8-0db293b662a3	Y	2025-09-04 12:43:24.556227	1	2025-09-04 12:43:24.556227	1	PO016	2025-09-04	2025-09-25	831afbb3-5f7a-4232-b7ed-136ced9184da	76, 100 Feet Road, Vadapalani, Chennai - 600026	12-I,Shanthi Apartments,Nehru nagar, Porur - 600012	1119.00
952180fc-974f-4b7e-a88b-f366856b7b36	Y	2025-09-05 15:30:21.714847	1	2025-09-05 15:30:21.714847	1	PO026	2025-09-05	2025-10-11	831afbb3-5f7a-4232-b7ed-136ced9184da	Old No. 19, New No. 38, Ranganathan Street, T. Nagar, Chennai - 600017	No. 88, ECR Road, Thiruvanmiyur, Chennai - 600041	1202.00
4d153d2b-c818-4371-bc41-14a4ec5a6137	Y	2025-09-04 12:36:27.156434	1	2025-09-04 12:36:27.156434	1	PO010	2025-09-04	2025-09-10	831afbb3-5f7a-4232-b7ed-136ced9184da	Old No. 19, New No. 38, Ranganathan Street, T. Nagar, Chennai - 600017	58, Anna Salai, Teynampet, Chennai - 600018	1398.00
bdb8049e-9515-44e4-81d9-64e9ee19651e	Y	2025-09-04 12:38:41.812752	1	2025-09-04 12:38:41.812752	1	PO012	2025-09-04	2025-09-12	a25f93be-9f3c-4e52-93a5-db1c3a5554d1	3-I, Krishna Accolade, Jayachandran Nagar, 2nd street, Medavakkam - 600096	Villa 10, Green Meadows, OMR, Sholinganallur, Chennai - 600119	900.00
8d4ffd18-9227-4ac4-97e6-4157e78116e1	Y	2025-09-05 15:31:13.568109	1	2025-09-05 15:31:13.568109	1	PO027	2025-09-05	2025-09-18	f93f4689-e5ff-4010-93b5-02c284466be6	Old No. 19, New No. 38, Ranganathan Street, T. Nagar, Chennai - 600017	23, Velachery Main Road, Velachery, Chennai - 600042	1275.00
7f9df552-ed6b-4ec9-b12e-7bd61a6b1314	Y	2025-09-05 15:32:08.879641	1	2025-09-05 15:32:08.879641	1	PO028	2025-09-05	2025-09-18	a25f93be-9f3c-4e52-93a5-db1c3a5554d1	3-I, Krishna Accolade, Jayachandran Nagar, 2nd street, Medavakkam - 600096	12-I,Shanthi Apartments,Nehru nagar, Porur - 600012	1270.00
82cbc740-6cd7-4e6e-8f26-b0bfb3b34194	Y	2025-09-05 15:33:19.128702	1	2025-09-05 15:33:19.128702	1	PO029	2025-09-05	2025-09-18	370d2489-da21-403e-bfd6-34ded5e4f3ac	17, Cathedral Road, Gopalapuram, Chennai  - 600086	15, Medavakkam Main Road, Madipakkam, Chennai  - 600091	1251.00
f72083ac-9774-4b4b-b034-08a90c63f5cf	Y	2025-09-05 15:34:22.838759	1	2025-09-05 15:34:22.838759	1	PO030	2025-09-05	2025-09-17	e797606c-3908-4c79-8f5a-75f327aa62f0	190, Old Mahabalipuram Road (OMR), Sholinganallur, Chennai  - 600119	Door No. 23, 2nd Avenue, Anna Nagar, Chennai - 600040	1504.00
d2716874-78ee-472e-9b4d-70bc8f36db28	Y	2025-09-05 15:35:19.505484	1	2025-09-05 15:35:19.505484	1	PO031	2025-09-05	2025-09-11	831afbb3-5f7a-4232-b7ed-136ced9184da	45, Mount Road, Guindy, Chennai - 600032	190, Old Mahabalipuram Road (OMR), Sholinganallur, Chennai - 600119	1960.00
fd3313d9-86b9-478a-aac2-2cd91b92da3f	Y	2025-09-08 11:25:35.593925	1	2025-09-08 11:25:35.593925	1	PO033	2025-09-08	2025-09-18	f93f4689-e5ff-4010-93b5-02c284466be6	No.316, Kotturpuram, Adyar - 600079	No.234, Teacher's colony, Lake view road, guindy - 600023	1160.00
89edf233-dbb8-46c3-bc4c-bddda851a333	Y	2025-09-05 15:36:28.679726	1	2025-09-05 15:36:28.679726	1	PO032	2025-09-05	2025-09-18	f93f4689-e5ff-4010-93b5-02c284466be6	45, North Usman Road, T. Nagar, Chennai - 600017	Plot No. 77, 3rd Cross Street, Neelankarai, Chennai - 600115	2122.00
c2cd34f9-99c6-4131-9246-56f6b5941be1	Y	2025-09-04 14:30:47.939365	1	2025-09-04 14:30:47.939365	1	PO020	2025-09-04	2025-09-24	a25f93be-9f3c-4e52-93a5-db1c3a5554d1	12-I,Shanthi Apartments,Nehru nagar, Porur - 600012	Flat 3B, Rainbow Apartments, Velachery Main Road, Chennai - 600042	2029.00
630d3e5c-5109-4157-8571-98b766ddc447	Y	2025-09-10 11:13:18.647643	1	2025-09-10 11:13:18.647643	1	PO034	2025-09-10	2025-09-11	e797606c-3908-4c79-8f5a-75f327aa62f0	Old No. 19, New No. 38, Ranganathan Street, T. Nagar, Chennai - 600017	23, Velachery Main Road, Velachery, Chennai - 600042	810.00
7f8d033b-25cb-41f1-8066-b63f3e36cb18	Y	2025-09-10 11:14:22.480136	1	2025-09-10 11:14:22.480136	1	PO035	2025-09-10	2025-09-19	a25f93be-9f3c-4e52-93a5-db1c3a5554d1	58, Anna Salai, Teynampet, Chennai - 600018	No. 15, 5th Main Road, Besant Nagar, Chennai - 600090	2114.00
8bf105f3-da59-4870-9357-9da8f022eaa3	Y	2025-09-04 12:45:04.424895	1	2025-09-04 12:45:04.424895	1	PO017	2025-09-04	2025-09-18	e797606c-3908-4c79-8f5a-75f327aa62f0	Door No. 23, 2nd Avenue, Anna Nagar, Chennai - 600040	17, Cathedral Road, Gopalapuram, Chennai - 600086	910.00
c8a51d28-afbc-49d2-9c2a-7935516f0c55	Y	2025-09-11 05:40:02.391286	1	2025-09-11 05:40:02.391286	1	PO036	2025-09-11	2025-09-12	a25f93be-9f3c-4e52-93a5-db1c3a5554d1	Old No. 19, New No. 38, Ranganathan Street, T. Nagar, Chennai - 600017	3-I, Krishna Accolade, Jayachandran Nagar, 2nd street, Medavakkam - 600096	623.00
f3a9e6d1-767a-42a7-a725-64babe3ff19e	Y	2025-09-11 05:41:03.13219	1	2025-09-11 05:41:03.13219	1	PO037	2025-09-11	2025-09-20	a25f93be-9f3c-4e52-93a5-db1c3a5554d1	3-I, Krishna Accolade, Jayachandran Nagar, 2nd street, Medavakkam - 600096	Shop No. 5, Luz Corner, Mylapore, Chennai - 600004	810.00
\.


--
-- Data for Name: purchaseorderline; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.purchaseorderline (orderline_id, isactive, created, createdby, updated, updatedby, order_id, product_id, price, quantity, amount, uom) FROM stdin;
9dd95366-a517-4046-9b4f-cdb76848fae7	Y	2025-09-04 12:37:44.731757	1	2025-09-04 12:37:44.731757	1	5cba6c89-66d8-4901-a443-647463a02f5e	7642c43e-77a4-4b58-b03e-b3033f1d947c	10.00	98	980.00	pcs
f9accfde-ffa6-45d9-9587-c1a15e24c671	Y	2025-09-04 12:37:44.770664	1	2025-09-04 12:37:44.770664	1	5cba6c89-66d8-4901-a443-647463a02f5e	8342a039-f732-42f0-aabc-bab0562e5086	14.00	5	70.00	pcs
d11c8854-fb6a-464c-a6cb-19586bd328e3	Y	2025-09-04 12:39:57.175935	1	2025-09-04 12:39:57.175935	1	c1763462-9684-4588-aaf7-6719763133ab	1a007919-65f3-481f-9f8b-26381d72be10	80.00	9	720.00	pcs
45090396-bfb0-41ac-b494-b83df401e08d	Y	2025-09-04 12:39:57.201589	1	2025-09-04 12:39:57.201589	1	c1763462-9684-4588-aaf7-6719763133ab	3c3dbfce-c45c-4f91-9052-0dc81358cbb6	90.00	8	720.00	pcs
f3cc9ffe-2072-42ef-b5cd-2e343597afd0	Y	2025-09-04 12:41:31.95172	1	2025-09-04 12:41:31.95172	1	b20db870-eebd-4bd3-8a51-490c6dc1c0ae	8e1d0043-4e53-4e0f-a0dd-b9b851d5c56e	70.00	8	560.00	Kg
e2f4246c-5a0b-44f0-818f-2c6efe46bff3	Y	2025-09-04 12:41:31.980664	1	2025-09-04 12:41:31.980664	1	b20db870-eebd-4bd3-8a51-490c6dc1c0ae	1a007919-65f3-481f-9f8b-26381d72be10	69.00	9	621.00	pcs
991846a2-b3a4-499c-89cb-9a4eb33bacd8	Y	2025-09-04 12:41:32.006137	1	2025-09-04 12:41:32.006137	1	b20db870-eebd-4bd3-8a51-490c6dc1c0ae	2bff7c6b-e846-4671-8a54-fb7d81435a88	89.00	5	445.00	Kg
4d379752-8910-4512-bf25-be24b9156614	Y	2025-09-04 12:41:32.03054	1	2025-09-04 12:41:32.03054	1	b20db870-eebd-4bd3-8a51-490c6dc1c0ae	651f0b2e-fd59-4ad3-b43f-32568e9eb83c	38.00	4	152.00	Kg
5a7991e8-f521-44cf-a3dc-cc148d940c8f	Y	2025-09-04 12:42:26.072261	1	2025-09-04 12:42:26.072261	1	de9273d5-b8e9-4241-b309-18011675b0e6	3c3dbfce-c45c-4f91-9052-0dc81358cbb6	87.00	14	1218.00	pcs
94711a19-3a85-445e-b968-20cdac06b0ce	Y	2025-09-04 14:29:56.376439	1	2025-09-04 14:29:56.376439	1	81867946-0eb5-49ce-8b27-636d8a3b97eb	7642c43e-77a4-4b58-b03e-b3033f1d947c	50.00	15	750.00	pcs
f800075b-319e-42ac-8c6a-d9c3f4545434	Y	2025-09-04 14:29:56.415247	1	2025-09-04 14:29:56.415247	1	81867946-0eb5-49ce-8b27-636d8a3b97eb	2bff7c6b-e846-4671-8a54-fb7d81435a88	89.00	8	712.00	Kg
2613df45-e6e3-4942-bb6c-344e507d8d65	Y	2025-09-04 15:22:24.482434	1	2025-09-04 15:22:24.482434	1	aa660b24-59b3-4fd9-b3d2-d9b08cc3e9b2	1091d3f2-aa71-467b-9c17-39082e550677	88.00	4	352.00	Kg
7a10a3eb-add2-4f6d-b86a-b6a54dd18d8a	Y	2025-09-04 15:22:24.552968	1	2025-09-04 15:22:24.552968	1	aa660b24-59b3-4fd9-b3d2-d9b08cc3e9b2	1e76fe71-852c-4213-a9af-036266bf67c1	135.00	12	1620.00	Kg
aeea1a85-67d6-4cec-9161-43ad33d04457	Y	2025-09-04 15:22:24.595157	1	2025-09-04 15:22:24.595157	1	aa660b24-59b3-4fd9-b3d2-d9b08cc3e9b2	2bff7c6b-e846-4671-8a54-fb7d81435a88	87.00	5	435.00	Kg
e892dcd7-bc96-4bd9-bd9e-45787172f814	Y	2025-09-04 15:22:56.531145	1	2025-09-04 15:22:56.531145	1	396617ed-3405-42ee-8ff8-0db293b662a3	1e76fe71-852c-4213-a9af-036266bf67c1	98.00	8	784.00	Kg
edf2224c-85f9-4f25-b788-e35c83ea0a13	Y	2025-09-04 15:22:56.575817	1	2025-09-04 15:22:56.575817	1	396617ed-3405-42ee-8ff8-0db293b662a3	1a007919-65f3-481f-9f8b-26381d72be10	67.00	5	335.00	pcs
eedec37c-82ad-4228-aa9f-96f86837cf58	Y	2025-09-04 15:36:55.300307	1	2025-09-04 15:36:55.300307	1	4d153d2b-c818-4371-bc41-14a4ec5a6137	2bff7c6b-e846-4671-8a54-fb7d81435a88	98.00	7	686.00	Kg
0db83a06-b41b-41e8-b75c-99253cc91c3d	Y	2025-09-04 15:36:55.340331	1	2025-09-04 15:36:55.340331	1	4d153d2b-c818-4371-bc41-14a4ec5a6137	3c3dbfce-c45c-4f91-9052-0dc81358cbb6	89.00	8	712.00	pcs
91f4d641-f0f9-4475-80ed-9283675df5b5	Y	2025-09-05 12:21:35.672625	1	2025-09-05 12:21:35.672625	1	bdb8049e-9515-44e4-81d9-64e9ee19651e	3c3dbfce-c45c-4f91-9052-0dc81358cbb6	100.00	9	900.00	pcs
12778170-9b77-443a-8127-41029ca06967	Y	2025-09-05 13:06:44.238979	1	2025-09-05 13:06:44.238979	1	4a9349a7-e6fd-4e3c-9e08-69de713787b3	3c3dbfce-c45c-4f91-9052-0dc81358cbb6	98.00	8	784.00	pcs
36924313-1ad5-4ad7-b90a-002447b679c9	Y	2025-09-05 13:06:44.272525	1	2025-09-05 13:06:44.272525	1	4a9349a7-e6fd-4e3c-9e08-69de713787b3	1091d3f2-aa71-467b-9c17-39082e550677	87.00	7	609.00	Kg
151c86b8-ceb2-425e-883a-899a0c0d5670	Y	2025-09-05 15:30:21.802133	1	2025-09-05 15:30:21.802133	1	952180fc-974f-4b7e-a88b-f366856b7b36	8342a039-f732-42f0-aabc-bab0562e5086	89.00	8	712.00	pcs
4bc4be1a-4a7f-4ed2-9550-7401f1a634c5	Y	2025-09-05 15:30:21.846344	1	2025-09-05 15:30:21.846344	1	952180fc-974f-4b7e-a88b-f366856b7b36	2bff7c6b-e846-4671-8a54-fb7d81435a88	70.00	7	490.00	Kg
62d2d8af-95e3-4dff-a127-c1691772da32	Y	2025-09-05 15:31:13.650729	1	2025-09-05 15:31:13.650729	1	8d4ffd18-9227-4ac4-97e6-4157e78116e1	7642c43e-77a4-4b58-b03e-b3033f1d947c	85.00	15	1275.00	pcs
e982bf9f-414c-46c4-90c7-a5b08008c3e4	Y	2025-09-05 15:32:08.967819	1	2025-09-05 15:32:08.967819	1	7f9df552-ed6b-4ec9-b12e-7bd61a6b1314	8e1d0043-4e53-4e0f-a0dd-b9b851d5c56e	70.00	9	630.00	Kg
7d4ae666-9cb4-418f-833a-486936994656	Y	2025-09-05 15:32:09.012694	1	2025-09-05 15:32:09.012694	1	7f9df552-ed6b-4ec9-b12e-7bd61a6b1314	651f0b2e-fd59-4ad3-b43f-32568e9eb83c	80.00	8	640.00	Kg
8e6b8cb9-640b-423f-ad4e-0d9ded2bb599	Y	2025-09-05 15:33:19.21533	1	2025-09-05 15:33:19.21533	1	82cbc740-6cd7-4e6e-8f26-b0bfb3b34194	1091d3f2-aa71-467b-9c17-39082e550677	90.00	7	630.00	Kg
0aa5d41f-eb9b-4878-83e1-8ff31c1c1e43	Y	2025-09-05 15:33:19.258197	1	2025-09-05 15:33:19.258197	1	82cbc740-6cd7-4e6e-8f26-b0bfb3b34194	2bff7c6b-e846-4671-8a54-fb7d81435a88	69.00	9	621.00	Kg
6b687e86-cf47-4d36-93c5-1a248b1f5390	Y	2025-09-05 15:34:22.92467	1	2025-09-05 15:34:22.92467	1	f72083ac-9774-4b4b-b034-08a90c63f5cf	1091d3f2-aa71-467b-9c17-39082e550677	98.00	8	784.00	Kg
8935b1fc-c780-405e-9561-89c9aed46f82	Y	2025-09-05 15:34:22.964706	1	2025-09-05 15:34:22.964706	1	f72083ac-9774-4b4b-b034-08a90c63f5cf	3c3dbfce-c45c-4f91-9052-0dc81358cbb6	80.00	9	720.00	pcs
f116c30f-02ef-47fd-84c7-ecab6ca50e4a	Y	2025-09-06 10:20:24.278058	1	2025-09-06 10:20:24.278058	1	d2716874-78ee-472e-9b4d-70bc8f36db28	8342a039-f732-42f0-aabc-bab0562e5086	30.00	12	360.00	pcs
028a1a5c-2258-4990-8a76-97c4352e1e68	Y	2025-09-06 10:20:24.32416	1	2025-09-06 10:20:24.32416	1	d2716874-78ee-472e-9b4d-70bc8f36db28	1a007919-65f3-481f-9f8b-26381d72be10	80.00	20	1600.00	pcs
66ac7604-5b6b-4a46-aafa-c6af933481fe	Y	2025-09-08 11:25:35.651953	1	2025-09-08 11:25:35.651953	1	fd3313d9-86b9-478a-aac2-2cd91b92da3f	7642c43e-77a4-4b58-b03e-b3033f1d947c	56.00	5	280.00	pcs
fe157d91-de5b-411f-bb17-f21a6f3de93b	Y	2025-09-08 11:25:35.683372	1	2025-09-08 11:25:35.683372	1	fd3313d9-86b9-478a-aac2-2cd91b92da3f	3c3dbfce-c45c-4f91-9052-0dc81358cbb6	80.00	11	880.00	pcs
a2e5dfde-f571-4807-b70b-83aa58892723	Y	2025-09-08 12:12:22.441733	1	2025-09-08 12:12:22.441733	1	89edf233-dbb8-46c3-bc4c-bddda851a333	651f0b2e-fd59-4ad3-b43f-32568e9eb83c	90.00	9	810.00	Kg
b091e179-a876-42ac-9fb4-5ea4e5468f8c	Y	2025-09-08 12:12:22.471697	1	2025-09-08 12:12:22.471697	1	89edf233-dbb8-46c3-bc4c-bddda851a333	1e76fe71-852c-4213-a9af-036266bf67c1	120.00	5	600.00	Kg
99f2ca7e-87f0-4153-890e-7e72f29afe69	Y	2025-09-08 12:12:22.495511	1	2025-09-08 12:12:22.495511	1	89edf233-dbb8-46c3-bc4c-bddda851a333	1a007919-65f3-481f-9f8b-26381d72be10	89.00	8	712.00	pcs
bebe03aa-5139-408a-a398-af06bef15bf6	Y	2025-09-09 10:52:42.182239	1	2025-09-09 10:52:42.182239	1	c2cd34f9-99c6-4131-9246-56f6b5941be1	2bff7c6b-e846-4671-8a54-fb7d81435a88	87.00	7	609.00	Kg
65bb62e4-988c-46fe-abe9-8b42c0701cd3	Y	2025-09-09 10:52:42.223146	1	2025-09-09 10:52:42.223146	1	c2cd34f9-99c6-4131-9246-56f6b5941be1	1a007919-65f3-481f-9f8b-26381d72be10	80.00	8	640.00	pcs
461a0a92-425f-40ba-a238-7199d9f5ff40	Y	2025-09-09 10:52:42.261511	1	2025-09-09 10:52:42.261511	1	c2cd34f9-99c6-4131-9246-56f6b5941be1	1091d3f2-aa71-467b-9c17-39082e550677	78.00	10	780.00	Kg
22125ff6-f8a7-43a2-be7a-07fa4cc650f0	Y	2025-09-10 11:13:18.726946	1	2025-09-10 11:13:18.726946	1	630d3e5c-5109-4157-8571-98b766ddc447	1091d3f2-aa71-467b-9c17-39082e550677	90.00	9	810.00	Kg
eef511ba-9cc6-4277-b5da-13fd0dbd2926	Y	2025-09-10 11:14:22.537306	1	2025-09-10 11:14:22.537306	1	7f8d033b-25cb-41f1-8066-b63f3e36cb18	1a007919-65f3-481f-9f8b-26381d72be10	88.00	8	704.00	pcs
0ca26d81-943e-4f6e-80d3-398daf0b7f41	Y	2025-09-10 11:14:22.568999	1	2025-09-10 11:14:22.568999	1	7f8d033b-25cb-41f1-8066-b63f3e36cb18	1e76fe71-852c-4213-a9af-036266bf67c1	235.00	6	1410.00	Kg
64971563-7553-4d2b-8fb0-d54e06418af0	Y	2025-09-10 07:35:15.436535	1	2025-09-10 07:35:15.436535	1	8bf105f3-da59-4870-9357-9da8f022eaa3	8342a039-f732-42f0-aabc-bab0562e5086	50.00	7	350.00	pcs
e6ae8859-d9d9-4137-89a2-50910c8fc247	Y	2025-09-10 07:35:15.473068	1	2025-09-10 07:35:15.473068	1	8bf105f3-da59-4870-9357-9da8f022eaa3	1a007919-65f3-481f-9f8b-26381d72be10	80.00	7	560.00	pcs
a2fcc04d-7fce-4085-a4fe-c1661fedccc5	Y	2025-09-11 05:40:02.622661	1	2025-09-11 05:40:02.622661	1	c8a51d28-afbc-49d2-9c2a-7935516f0c55	3c3dbfce-c45c-4f91-9052-0dc81358cbb6	89.00	7	623.00	pcs
15389a15-2c7e-4640-b9eb-52b5a987c469	Y	2025-09-11 05:41:03.233782	1	2025-09-11 05:41:03.233782	1	f3a9e6d1-767a-42a7-a725-64babe3ff19e	651f0b2e-fd59-4ad3-b43f-32568e9eb83c	90.00	9	810.00	Kg
\.


--
-- Data for Name: userinfo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.userinfo (userinfo_id, isactive, created, createdby, updated, updatedby, value, name) FROM stdin;
1	Y	2025-03-14 12:32:41.06639	\N	2025-03-14 12:32:41.06639	\N	admin01	Gobi
2	Y	2025-03-14 12:32:41.06639	\N	2025-03-14 12:32:41.06639	\N	admin02	Arjun
\.


--
-- Data for Name: vendor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vendor (vendor_id, isactive, created, createdby, updated, updatedby, name, value, address) FROM stdin;
f93f4689-e5ff-4010-93b5-02c284466be6	Y	2025-03-14 16:54:08.610756	1	2025-03-14 16:54:08.610756	1	OnO Goods Pvt. Ltd	V001	No. 123, Anna Nagar, Chennai, Tamil Nadu, India
e797606c-3908-4c79-8f5a-75f327aa62f0	Y	2025-03-14 16:54:08.610756	1	2025-03-14 16:54:08.610756	1	Amul Dairy Products	V002	No. 456, Gandhipuram, Coimbatore, Tamil Nadu, India
a25f93be-9f3c-4e52-93a5-db1c3a5554d1	Y	2025-03-14 16:54:08.610756	1	2025-03-14 16:54:08.610756	1	Ninjacart	V003	No. 789, Velur, Paramathi, Tamil Nadu, India
370d2489-da21-403e-bfd6-34ded5e4f3ac	Y	2025-03-14 16:54:08.610756	1	2025-03-14 16:54:08.610756	1	Aachi Spices Pvt. Ltd	V004	No. 101, Thiruchengodu, Namakkal, Tamil Nadu, India
831afbb3-5f7a-4232-b7ed-136ced9184da	Y	2025-03-14 16:54:08.610756	1	2025-03-14 16:54:08.610756	1	Nandhi Goods Pvt. Ltd	V005	No. 202, Kangyam, Erode, Tamil Nadu, India
\.


--
-- Name: product product_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_name_key UNIQUE (name);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (product_id);


--
-- Name: purchaseorder purchaseorder_document_no_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchaseorder
    ADD CONSTRAINT purchaseorder_document_no_key UNIQUE (document_no);


--
-- Name: purchaseorder purchaseorder_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchaseorder
    ADD CONSTRAINT purchaseorder_pkey PRIMARY KEY (order_id);


--
-- Name: purchaseorderline purchaseorderline_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchaseorderline
    ADD CONSTRAINT purchaseorderline_pkey PRIMARY KEY (orderline_id);


--
-- Name: userinfo userinfo_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.userinfo
    ADD CONSTRAINT userinfo_name_key UNIQUE (name);


--
-- Name: userinfo userinfo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.userinfo
    ADD CONSTRAINT userinfo_pkey PRIMARY KEY (userinfo_id);


--
-- Name: vendor vendor_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendor
    ADD CONSTRAINT vendor_name_key UNIQUE (name);


--
-- Name: vendor vendor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendor
    ADD CONSTRAINT vendor_pkey PRIMARY KEY (vendor_id);


--
-- Name: purchaseorder purchaseorder_vendor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchaseorder
    ADD CONSTRAINT purchaseorder_vendor_id_fkey FOREIGN KEY (vendor_id) REFERENCES public.vendor(vendor_id);


--
-- Name: purchaseorder purchaseorder_vendor_id_fkey1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchaseorder
    ADD CONSTRAINT purchaseorder_vendor_id_fkey1 FOREIGN KEY (vendor_id) REFERENCES public.vendor(vendor_id);


--
-- Name: purchaseorderline purchaseorderline_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchaseorderline
    ADD CONSTRAINT purchaseorderline_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.purchaseorder(order_id);


--
-- Name: purchaseorderline purchaseorderline_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchaseorderline
    ADD CONSTRAINT purchaseorderline_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.product(product_id);


--
-- PostgreSQL database dump complete
--

