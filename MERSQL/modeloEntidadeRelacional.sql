--# PerfilAcesso        (id,nome);
--# Empresa             (id,nome,horaInicialTrabalho,horaFinalTrabalho,intervaloAgendamentoGeralServico,periodoMinimoCancelamento,diaSemanaTrabalho,telefone,email);
--# Usuario             (id,email,senha,celular,fotoPerfil,ativo,perfil);
--# Pessoa              (id,nome,dataNascimento,USUARIO);
--# Cliente             (id,PESSOA);
--# Funcionario         (id,PESSOA);
--# CategoriaServico    (id,nome,descricao,categoriaPai,ativo);
--# Servico             (id,nome,descricao,valor,duracao,categoria,funcionarios,camposadicionais);
--# CampoAdicional      (id,nome,descricao,tipo)
--# TipoCampoAdicional  (id,nome)
--# StatusAgendamento	(id,nome)
--# Agendamento			(id,CLIENTE,dataAgendamento,horarioAgendamento,SERVICO,FUNCIONARIO,STATUS)

--# SQL - Tabela

drop schema if exists sistema cascade;
create schema sistema;
set schema 'sistema';

create table PerfilAcesso(
  id integer,
  nome varchar(50),
  constraint pkPerfilAcesso primary key (id)
);

create table Empresa(
  id integer,
  nome varchar(100) not null,
  horaInicialTrabalho time not null,
  horaFinalTrabalho time not null,
  intervaloAgendamentoGeralServico interval not null,
  periodoMinimoCancelamento interval not null,
	diaSemanaTrabalho  integer[],
	telefone bigint[] not null,
	email varchar not null,
  constraint pkEmpresa primary key (id)
);

create table Usuario(
  id integer,
  email varchar(100) not null,
  senha varchar(500) not null,
  fotoPerfil bytea,
  celular bigint not null,
  perfil integer not null,
  ativo boolean default TRUE NOT NULL,
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
  ativo boolean default true not null,
  constraint pkCategoriaServico primary key (id),
  constraint unqNomeCategoria unique (nome,categoriaPai),
  constraint fkCategoriaPai foreign key (categoriaPai) references CategoriaServico(id)
);

create table Servico(
  id integer,
  nome varchar(100) not null,
  descricao varchar(500),
  valor money,
  duracao interval,
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
  funcionario integer not null,
  status integer not null,
  dataHoraInclusao timestamp not null,
  dataHoraAlteracao timestamp not null,
  constraint pkAgendamento primary key (id),
  constraint fkCliente foreign key (cliente) references Cliente(id),
  constraint fkServico foreign key (servico) references Servico(id),
  constraint fkFuncionario foreign key (funcionario) references Funcionario(id),
  constraint fkStatusAgendamento foreign key (status) references StatusAgendamento(id),
  constraint unqAgendamento unique (dataAgendamento,horarioAgendamento,cliente,funcionario,servico,status)
);

alter table Agendamento alter dataHoraInclusao set default now();
alter table Agendamento alter dataHoraAlteracao set default now();

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

create table bloqueioAgenda 
(
id integer,
dataBloqueio date not null,
horaInicial time not null,
duracao interval not null,
funcionario integer,
constraint pkBloqueioAgenda primary key (id),
constraint fkFuncionario foreign key (funcionario) references Funcionario(id),
constraint unqBloqueioAgenda unique (dataBloqueio,horaInicial,funcionario)
);

--###########
create sequence sistema.sqn_empresa;
create sequence sistema.sqn_usuario;
create sequence sistema.sqn_servico;
create sequence sistema.sqn_cliente;
create sequence sistema.sqn_pessoa;
create sequence sistema.sqn_funcionario;
create sequence sistema.sqn_agendamento;
create sequence sistema.sqn_categoriaservico;
create sequence sistema.sqn_campoadicional;
create sequence sistema.sqn_tipocampoadicional;
create sequence sistema.sqn_bloqueioAgenda;

--###########

insert into PerfilAcesso values
  (1,'CLIENTECOMUM'),
  (2,'FUNCIONARIOCOMUM'),
  (3,'FUNCIONARIOADMIN');

insert into Empresa (id, nome, horaInicialTrabalho, horaFinalTrabalho, intervaloAgendamentoGeralServico, periodoMinimoCancelamento, diaSemanaTrabalho,telefone,email) values 
  (nextval('sqn_empresa'),'Mafera Soft. Testes','08:00:00','17:00:00','00:30:00'::INTERVAL,'48:00:00'::INTERVAL,Array[1,2,3,4,5,6],Array[1139551324,11982231234],'empresa@empresa.com');

insert into StatusAgendamento values
  (1,'AGUARDANDOATENDIMENTO'),
  (2,'FINALIZADO'),
  (3,'CANCELADO');

insert into Usuario (id, email, senha, celular, perfil) values
  (nextval('sqn_usuario'),'admin@admin.com','$2a$12$Tkpv4hAeB2gLeI.JVn0Kx.eaN/DRw9P4L79JvNrbCSS9fQy.dgnd.',11912341234,3),
  (nextval('sqn_usuario'),'felipe@funcionario.com','$2a$12$cvkQ9ciPRuvLvaYiXG9DtejyWNS6GMYtWoVpHFtllmfIUIIPAcr36',11123123123,2),
  (nextval('sqn_usuario'),'matheus@funcionario.com','$2a$12$cvkQ9ciPRuvLvaYiXG9DtejyWNS6GMYtWoVpHFtllmfIUIIPAcr36',11845784567,2),
  (nextval('sqn_usuario'),'rafael@funcionario.com','$2a$12$cvkQ9ciPRuvLvaYiXG9DtejyWNS6GMYtWoVpHFtllmfIUIIPAcr36',11932145678,2),
  (nextval('sqn_usuario'),'nathalia@cliente.com','$2a$12$cvkQ9ciPRuvLvaYiXG9DtejyWNS6GMYtWoVpHFtllmfIUIIPAcr36',11963970577,1),
  (nextval('sqn_usuario'),'brunolopes@cliente.com','$2a$12$cvkQ9ciPRuvLvaYiXG9DtejyWNS6GMYtWoVpHFtllmfIUIIPAcr36',11955445566,1);

insert into CategoriaServico values
	(0,'DEFAULT','Todas categorias serão filhas dessas categoria.',null),
	(1,'Corte','Teste',0),
	(2,'Masculino','Testes',1),
	(3,'Barba','...',2),
	(4,'Cabelo','Cortamos diversos tipos de cabelos',2),
	(5,'Feminino','Teste feminino',1),
	(6,'Cabelo','Cortes de cabelos lindos',5),
	(7,'Massagem','Massagens de todos os tipos',0),
	(8,'Depilacao','Todos os tipos de depilação',0),
	(9,'Mulheres','Toda mulher precisa se sentir linda',8),
	(10,'Perna','Depilação a laser',9),
	(11,'Masculino','Todo homem precisa sentir-se lindo',8),
	(12,'Axilas','Todas axilas precisam estar cheirosas',11),
	(13,'Hardware','Equipamentos de informatica',0),
	(14,'Monitor','Display LCD',13),
	(15,'Gabinete','...',13),
	(16,'Placa de Vídeo','VGA/HDMI',13),
	(17,'Placa-mãe','Núcleo do computador',13),
	(18,'slot','...',17),
	(19,'Manutenção','Todos tipos de manutenção que a empresa atende',0),
	(20,'Hidraúlica','...',19),
	(21,'Elétrica','Qualquer tipo de manutenção com eletricidade',19);

alter sequence sistema.sqn_categoriaservico restart with 21;

insert into Servico values
	(nextval('sqn_servico'),'Corte de Cabelo Masculino','Diversos cortes para os homens','25.90'::NUMERIC::MONEY,'PT30M'::INTERVAL,4,'{"1","2"}'),
	(nextval('sqn_servico'),'Teste','Desenvolvimento','2500.90'::NUMERIC::MONEY,'PT240M'::INTERVAL,19,'{"2"}');
 
insert into Pessoa values
  (nextval('sqn_pessoa'),'Felipe Jesus','01/04/1992',2),
  (nextval('sqn_pessoa'),'Matheus Nascimento','10/10/1998',3),
  (nextval('sqn_pessoa'),'Rafael Pereira','01/04/1992',4),
  (nextval('sqn_pessoa'),'Nathalia Cardoso','05/04/2000',5),
  (nextval('sqn_pessoa'),'Bruno Lopes','01/01/1990',6);
  
insert into Funcionario values
  (nextval('sqn_funcionario'),1),
  (nextval('sqn_funcionario'),2),
  (nextval('sqn_funcionario'),3);
  
insert into Cliente values
  (nextval('sqn_cliente'),4),
  (nextval('sqn_cliente'),5);

insert into Agendamento (id,dataAgendamento,horarioAgendamento,cliente,servico,funcionario,status) values
  (nextval('sqn_agendamento'),'2020-10-29','16:00:00',1,1,1,1),
  (nextval('sqn_agendamento'),'2020-10-31','09:00:00',1,1,1,1),
  (nextval('sqn_agendamento'),'2020-10-31','11:30:00',1,1,2,1),
  (nextval('sqn_agendamento'),'2020-10-31','12:00:00',1,1,1,1),
  (nextval('sqn_agendamento'),'2020-10-30','08:30:00',1,1,2,1),
  (nextval('sqn_agendamento'),'2020-11-01','08:00:00',1,1,2,1),
  (nextval('sqn_agendamento'),'2020-10-31','12:00:00',2,2,2,1);
  
insert into BloqueioAgenda (id,dataBloqueio,horaInicial,duracao,funcionario) values
(nextval('sqn_bloqueioAgenda'),'2020-11-02','00:00','PT1440M'::INTERVAL,1),
(nextval('sqn_bloqueioAgenda'),'2020-11-23','15:00','PT30M'::INTERVAL,2);
