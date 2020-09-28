select tab_func.* from
(
	select
		 age.id as agendamento
		,age.dataAgendamento
		,age.horarioAgendamento
		,age.horarioAgendamento + ser.duracao as horarioFinalAgendamento
		,age.servico as servico
		,age.funcionario
		,age.cliente
	from sistema.agendamento age
	inner join sistema.servico ser
	on age.servico = ser.id
	where
		age.dataAgendamento = '2020-09-30'::DATE
		and age.funcionario = 2
) as tab_func
inner join
(
	select
		t_servico.id,
		t1.horarioSolicitado,
		t1.horarioSolicitado + t_servico.duracao as horarioFinalSolicitado
	from (
		select
			1 as servico
			,'12:00'::TIME as horarioSolicitado
	) t1
	inner join sistema.servico t_servico
	on t_servico.id = t1.servico
) as tab_param
on (tab_param.horarioSolicitado >= tab_func.horarioAgendamento::time
AND tab_param.horarioSolicitado < tab_func.horarioFinalAgendamento::time)
OR (tab_param.horarioFinalSolicitado > tab_func.horarioAgendamento::time
AND tab_param.horarioFinalSolicitado < tab_func.horarioFinalAgendamento::time)
OR  (tab_func.horarioAgendamento::time >= tab_param.horarioSolicitado
AND tab_func.horarioAgendamento::time < tab_param.horarioFinalSolicitado)
OR  (tab_func.horarioFinalAgendamento::time > tab_param.horarioSolicitado
AND tab_func.horarioFinalAgendamento::time < tab_param.horarioFinalSolicitado)
UNION
select tab_cli.* from
(
	select
		 age.id as agendamento
		,age.dataAgendamento
		,age.horarioAgendamento
		,age.horarioAgendamento + ser.duracao as horarioFinalAgendamento
		,age.servico as servico
		,age.funcionario
		,age.cliente
	from sistema.agendamento age
	inner join sistema.servico ser
	on age.servico = ser.id
	where
		age.dataAgendamento = '2020-09-30'::DATE
		and age.cliente = 1
) as tab_cli
inner join
(
	select
		t_servico.id,
		t1.horarioSolicitado,
		t1.horarioSolicitado + t_servico.duracao as horarioFinalSolicitado
	from (
		select
			1 as servico
			,'12:00'::TIME as horarioSolicitado
	) t1
	inner join sistema.servico t_servico
	on t_servico.id = t1.servico
) as tab_param
on (tab_param.horarioSolicitado >= tab_cli.horarioAgendamento::time
AND tab_param.horarioSolicitado < tab_cli.horarioFinalAgendamento::time)
OR (tab_param.horarioFinalSolicitado > tab_cli.horarioAgendamento::time
AND tab_param.horarioFinalSolicitado < tab_cli.horarioFinalAgendamento::time)
OR  (tab_cli.horarioAgendamento::time >= tab_param.horarioSolicitado
AND tab_cli.horarioAgendamento::time < tab_param.horarioFinalSolicitado)
OR  (tab_cli.horarioFinalAgendamento::time > tab_param.horarioSolicitado
AND tab_cli.horarioFinalAgendamento::time < tab_param.horarioFinalSolicitado);