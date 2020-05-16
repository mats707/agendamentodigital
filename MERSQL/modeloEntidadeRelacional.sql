--# PerfilAcesso        (id,nome);
--# Usuario             (id,email,senha,celular,habilitado,perfil);
--# Pessoa              (id,nome,dataNascimento,USUARIO);
--# Cliente             (id,PESSOA);
--# Funcionario         (id,PESSOA);
--# CategoriaServico    (id,nome,descricao,categoriaPai);
--# Servico             (id,nome,descricao,valor,duracao,categoria,funcionarios,camposadicionais);
--# CampoAdicional      (id,nome,descricao,tipo)
--# TipoCampoAdicional  (id,nome)
--# StatusAgendamento	(id,nome)
--# Agendamento			(id,CLIENTE,dataAgendamento,horarioAgendamento,SERVICO,STATUS)
--# SQL - Tabela

drop schema sistema cascade;
create schema sistema;
set schema 'sistema';

create table PerfilAcesso(
  id integer,
  nome varchar(50),
  constraint pkPerfilAcesso primary key (id)
);

create table Usuario(
  id integer,
  email varchar(100) not null,
  senha varchar(500) not null,
  celular bigint not null,
  perfil integer not null,
  habilitado boolean default TRUE NOT NULL,
  constraint pkUsuario primary key (id),
  constraint unqUsuario unique (email),
  constraint unqCelularUsuario unique (celular),
  constraint fkPerfilAcesso foreign key (perfil) references PerfilAcesso(id)
);

create table Pessoa(
  id              integer,
  nome            varchar(100) not null,
  dataNascimento  date not null,
  usuario		  integer,
  constraint pkPessoa primary key (id),
  constraint fkUsuario     foreign key (usuario)      references Usuario(id)
);

create table Cliente(
  id integer,
  pessoa integer,
  constraint pkCliente          primary key (id),
  constraint unqPessoaCliente   unique (pessoa),
  constraint fkPessoa           foreign key (pessoa)    references Pessoa(id)
);

create table Funcionario(
  id integer,
  pessoa integer,
  constraint pkFuncionario    primary key (id),
  constraint unqFuncionario   unique (pessoa),
  constraint fkPessoa         foreign key (pessoa)    references Pessoa(id)
);

--##########################

create table CategoriaServico(
  id integer,
  nome varchar(100) not null,
  descricao varchar(500) default '' not null,
  categoriaPai integer default 0,
  constraint pkCategoriaServico primary key (id),
  constraint unqNomeCategoria unique (nome,categoriaPai),
  constraint fkCategoriaPai foreign key (categoriaPai) references CategoriaServico(id)
);

create table Servico(
  id integer,
  nome varchar(100) not null,
  descricao varchar(500),
  valor money,
  duracao time, --select cast('11:00 PM' as time);
  categoria integer,
  funcionarios integer[],
  camposadicionais integer[],
  constraint pkServico primary key (id),
  constraint unqNomeServico unique (nome),
  constraint fkCategoria foreign key (categoria) references CategoriaServico(id)
);

create table StatusAgendamento(
  id integer,
  nome varchar(50),
  constraint pkStatusAgendamento primary key (id)
);

create table Agendamento(
  id integer,
  dataAgendamento date not null,
  horarioAgendamento time not null,
  cliente integer not null,
  servico integer not null,
  status integer not null,
  constraint pkAgendamento primary key (id),
  constraint fkCliente foreign key (cliente) references Cliente(id),
  constraint fkServico foreign key (servico) references Servico(id),
  constraint fkStatusAgendamento foreign key (status) references StatusAgendamento(id)
);

create table TipoCampoAdicional(
  id integer,
  nome varchar(100) not null,
  descricao varchar(500),
  constraint pkTipoCampoAdicional primary key (id),
  constraint unqNomeTipoCampoAdicional unique (nome)
);

create table CampoAdicional(
  id integer,
  nome varchar(100) not null,
  descricao varchar(500),
  tipo integer,
  constraint pkCampoAdicional primary key (id),
  constraint unqNomeCampoAdicional unique (nome),
  constraint fkTipo foreign key (tipo) references TipoCampoAdicional(id)
);

--###########
create sequence sistema.sqn_usuario;
create sequence sistema.sqn_servico;
create sequence sistema.sqn_cliente;
create sequence sistema.sqn_pessoa;
create sequence sistema.sqn_funcionario;
create sequence sistema.sqn_categoriaservico;
create sequence sistema.sqn_campoadicional;
create sequence sistema.sqn_tipocampoadicional;

--###########

insert into PerfilAcesso values
  (1,'CLIENTECOMUM'),
  (2,'FUNCIONARIOCOMUM'),
  (3,'FUNCIONARIOADMIN');

insert into Usuario values
  (nextval('sqn_usuario'),'admin@admin.com','YWRtaW4=',11912341234,3),
  (nextval('sqn_usuario'),'felipe@funcionario.com','MTIzNDU2Nzg=',11123123123,2),
  (nextval('sqn_usuario'),'matheus@funcionario.com','MTIzNDU2Nzg=',11845784567,2),
  (nextval('sqn_usuario'),'rafael@funcionario.com','MTIzNDU2Nzg=',11932145678,2);

insert into CategoriaServico values
	(0,'DEFAULT','Todas categorias serão filhas dessas categoria.',null),
	(2,'Corte','Teste',0),
	(3,'Masculino','Testes',2),
	(8,'Feminino','Teste feminino',2),
	(9,'Barba','...',3),
	(10,'Cabelo','Cortamos diversos tipos de cabelos',3),
	(11,'Massagem','Massagens de todos os tipos',0),
	(12,'Depilacao','Todos os tipos de depilação',0),
	(13,'Cabelo','Cortes de cabelos lindos',8),
	(14,'Mulheres','Toda mulher precisa se sentir linda',12),
	(15,'Perna','Depilação a laser',14),
	(16,'Masculino','Todo homem precisa sentir-se lindo',12),
	(17,'Axilas','Todas axilas precisam estar cheirosas',16),
	(18,'Hardware','Equipamentos de informatica',0),
	(19,'Monitor','Display LCD',18),
	(20,'Gabinete','...',18),
	(21,'Placa de Vídeo','VGA/HDMI',18),
	(22,'Manutenção','Todos tipos de manutenção que a empresa atende',0),
	(23,'Hidraúlica','...',22),
	(24,'Elétrica','Qualquer tipo de manutenção com eletricidade',22),
	(25,'Placa-mãe','Núcleo do computador',18),
	(26,'slot','...',25);

alter sequence sistema.sqn_categoriaservico restart with 27;
 
insert into Pessoa values
  (nextval('sqn_pessoa'),'Felipe Jesus','01/04/1992',2),
  (nextval('sqn_pessoa'),'Matheus Nascimento','10/10/1998',3),
  (nextval('sqn_pessoa'),'Rafael Pereira','01/04/1992',4);
  
insert into Funcionario values
  (nextval('sqn_funcionario'),1),
  (nextval('sqn_funcionario'),2),
  (nextval('sqn_funcionario'),3);

