select * from
(
	select
		age.horarioAgendamento
		,age.horarioAgendamento + ser.duracao as horarioFinalAgendamento
		,ser.id as servico
		,age.funcionario
	from sistema.agendamento age
	inner join sistema.servico ser
	on age.servico = ser.id
) as t_func_ocupado
inner join
(
	select
		'2' as servico
		,'1' as cliente
		, '2' as funcionario
		,'2020-09-28'::DATE as dataSolicitada
		,'15:30'::TIME as horarioSolicitado
	from dual
) as t2
where t1.dataAgendamento::DATE = '2020-09-28'
and ('15:30'::TIME >= t1.horarioAgendamento::time
AND '15:30'::TIME < t1.horarioFinalAgendamento::time)
OR ('15:30'::TIME+'00:30'::INTERVAL > t1.horarioAgendamento::time
AND '15:30'::TIME+'00:30'::INTERVAL < t1.horarioFinalAgendamento::time)
OR  (t1.horarioAgendamento::time >= '15:30'::TIME
AND t1.horarioAgendamento::time < '15:30'::TIME+'00:30'::INTERVAL)
OR  (t1.horarioFinalAgendamento::time >= '15:30'::TIME
AND t1.horarioFinalAgendamento::time < '15:30'::TIME+'00:30'::INTERVAL);



-------------------------------------------
SELECT * FROM sistema.CategoriaServico
WHERE id = 25 AND categoriapai=0
UNION
SELECT * FROM sistema.CategoriaServico
WHERE categoriapai=25;

WITH categoria AS (
	SELECT id                 -- your PK
	FROM   sistema.CategoriaServico
	WHERE  (id = 25 AND categoriapai=0)  -- your condition
	OR (id in (SELECT id FROM sistema.CategoriaServico WHERE categoriapai=25))
	LIMIT  50000
	FOR    UPDATE             -- SKIP LOCKED ?
)
DELETE FROM sistema.CategoriaServico
USING categoria
WHERE  sistema.CategoriaServico.id = categoria.id;
