PGDMP       5            	    |            pacientes_db    16.4    16.4     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    38528    pacientes_db    DATABASE     �   CREATE DATABASE pacientes_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_El Salvador.1252';
    DROP DATABASE pacientes_db;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4            �            1259    38530 	   pacientes    TABLE     �   CREATE TABLE public.pacientes (
    id integer NOT NULL,
    nombre character varying(100) NOT NULL,
    genero character varying(10) NOT NULL,
    tipo_sangre character varying(3) NOT NULL,
    presion_arterial character varying(10) NOT NULL
);
    DROP TABLE public.pacientes;
       public         heap    postgres    false    4            �            1259    38529    pacientes_id_seq    SEQUENCE     �   CREATE SEQUENCE public.pacientes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.pacientes_id_seq;
       public          postgres    false    4    216            �           0    0    pacientes_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.pacientes_id_seq OWNED BY public.pacientes.id;
          public          postgres    false    215            �            1259    38537    usuarios    TABLE     �   CREATE TABLE public.usuarios (
    id integer NOT NULL,
    usuario character varying(50) NOT NULL,
    "contraseña" character varying(50) NOT NULL
);
    DROP TABLE public.usuarios;
       public         heap    postgres    false    4            �            1259    38536    usuarios_id_seq    SEQUENCE     �   CREATE SEQUENCE public.usuarios_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.usuarios_id_seq;
       public          postgres    false    218    4            �           0    0    usuarios_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.usuarios_id_seq OWNED BY public.usuarios.id;
          public          postgres    false    217            U           2604    38533    pacientes id    DEFAULT     l   ALTER TABLE ONLY public.pacientes ALTER COLUMN id SET DEFAULT nextval('public.pacientes_id_seq'::regclass);
 ;   ALTER TABLE public.pacientes ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            V           2604    38540    usuarios id    DEFAULT     j   ALTER TABLE ONLY public.usuarios ALTER COLUMN id SET DEFAULT nextval('public.usuarios_id_seq'::regclass);
 :   ALTER TABLE public.usuarios ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    218    218            �          0    38530 	   pacientes 
   TABLE DATA                 public          postgres    false    216   �       �          0    38537    usuarios 
   TABLE DATA                 public          postgres    false    218   "       �           0    0    pacientes_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.pacientes_id_seq', 41, true);
          public          postgres    false    215            �           0    0    usuarios_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.usuarios_id_seq', 1, true);
          public          postgres    false    217            X           2606    38535    pacientes pacientes_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.pacientes
    ADD CONSTRAINT pacientes_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.pacientes DROP CONSTRAINT pacientes_pkey;
       public            postgres    false    216            Z           2606    38542    usuarios usuarios_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.usuarios DROP CONSTRAINT usuarios_pkey;
       public            postgres    false    218            �   �   x���v
Q���W((M��L�+HL�L�+I-Vs�	uV�06�QP�
�9��IE� ���tJ�JT״��$�8K$�P��曚�I�q&H����f�!�SB��̑L3A1��Z f��0.. d�q      �   <   x���v
Q���W((M��L�+-.M,��/Vs�	uV�0�QPON-N,R�34���� C�     