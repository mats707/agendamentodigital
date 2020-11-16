select
	 tab_bloq.horaInicial
	,tab_bloq.horarioFinalBloqueio
	,tab_bloq.funcionario
from
(
	select
		 bloq.id as bloqueioAgenda
		,bloq.dataBloqueio
		,bloq.horaInicial
		,bloq.horaInicial + bloq.duracao as horarioFinalBloqueio
		,bloq.funcionario
	from sistema.BloqueioAgenda bloq
	where
		bloq.dataBloqueio = '2020-10-31'::DATE
		and bloq.funcionario = 1
) as tab_bloq
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
on (tab_param.horarioSolicitado >= tab_bloq.horaInicial::time
AND tab_param.horarioSolicitado < tab_bloq.horarioFinalBloqueio::time)
OR (tab_param.horarioFinalSolicitado > tab_bloq.horaInicial::time
AND tab_param.horarioFinalSolicitado < tab_bloq.horarioFinalBloqueio::time)
OR  (tab_bloq.horaInicial::time >= tab_param.horarioSolicitado
AND tab_bloq.horaInicial::time < tab_param.horarioFinalSolicitado)
OR  (tab_bloq.horarioFinalBloqueio::time > tab_param.horarioSolicitado
AND tab_bloq.horarioFinalBloqueio::time < tab_param.horarioFinalSolicitado)


