import './App.css'
import React, { useState } from 'react';

const TIPOS = [
  {
    key: 'RELATORIO',
    label: 'Relatório Gerencial'
  },
  {
    key: 'NOTA_FISCAL',
    label: 'Nota Fiscal'
  },
  {
    key: 'PROPOSTA',
    label: 'Proposta Comercial'
  },
  {
    key: 'CONTRATO',
    label: 'Contrato'
  },
];

const FORMATOS = [
  { key: 'PDF', label: 'PDF', color: '#4b7e4a' },
  { key: 'TXT', label: 'TXT', color: '#4b7e4a' },
  { key: 'HTML', label: 'HTML', color: '#4b7e4a' },
  { key: 'JSON', label: 'JSON', color: '#4b7e4a' }
];

const initRelatorio = {
  titulo: '',
  periodo: '',
  responsavel: '',
  indicadores: [{ nome: '', valor: '', resultado: '' }],
  observacoes: ''
};

const initNotaFiscal = {
  numeroNf: '',
  dataEmissao: '',
  nomeCliente: '',
  cpfCnpjCliente: '',
  enderecoCliente: '',
  itens: [{ descricao: '', quantidade: 1, valorUnitario: 0 }]
};

const initProposta = {
  numeroProposta: '',
  dataEmissao: '',
  validade: '',
  cliente: '',
  fornecedor: '',
  itens: [{ descricao: '', valor: 0 }],
  condicoesPagamento: ''
};

const initContrato = {
  numeroContrato: '',
  dataAssinatura: '',
  parteA: '',
  parteB: '',
  objeto: '',
  clausulas: [''],
  prazo: ''
};

function Field({ label, children }) {
  return (
    <div className="form-field">
      <label className="field-label">{label}</label>
      {children}
    </div>
  );
}

function FormProposta({ data, onChange }) {
  const h = e => onChange({ ...data, [e.target.name]: e.target.value });

  const setItem = (i, field, val) => {
    const itens = data.itens.map((it, idx) => idx === i ? { ...it, [field]: val } : it);
    onChange({ ...data, itens });
  };
  const addItem = () => onChange({ ...data, itens: [...data.itens, { descricao: '', valor: 0 }] });
  const removeItem = i => onChange({ ...data, itens: data.itens.filter((_, idx) => idx !== i) });

  const total = data.itens.reduce((s, it) => s + Number(it.valor), 0);

  return (
      <div className="form-grid">
        <div className="form-section-title">Identificação</div>
        <Field label="Número da Proposta"><input className="in" name="numeroProposta" value={data.numeroProposta} onChange={h} required /></Field>
        <Field label="Data de Emissão"><input className="in" name="dataEmissao" type="date" value={data.dataEmissao} onChange={h} required /></Field>
        <Field label="Validade"><input className="in" name="validade" type="date" value={data.validade} onChange={h} required /></Field>

        <div className="form-section-title">Partes</div>
        <Field label="Cliente"><input className="in" name="cliente" value={data.cliente} onChange={h} required /></Field>
        <Field label="Fornecedor"><input className="in" name="fornecedor" value={data.fornecedor} onChange={h} required /></Field>

        <div className="form-section-title">
          Itens
          <button type="button" className="btn-add" onClick={addItem}>+ Adicionar Item</button>
        </div>
        {data.itens.map((it, i) => (
            <div className="dynamic-row" key={i}>
              <input className="in" placeholder="Descrição" value={it.descricao} onChange={e => setItem(i, 'descricao', e.target.value)} />
              <input className="in in-sm" type="number" placeholder="Valor (R$)" step="0.01" value={it.valor} onChange={e => setItem(i, 'valor', e.target.value)} />
              {data.itens.length > 1 && (
                  <button type="button" className="btn-remove" onClick={() => removeItem(i)}>✕</button>
              )}
            </div>
        ))}
        <div className="total-bar">Total: <strong>R$ {total.toFixed(2)}</strong></div>

        <div className="form-section-title">Condições de Pagamento</div>
        <Field label="Condições de Pagamento">
          <textarea className="in in-textarea" name="condicoesPagamento" value={data.condicoesPagamento} onChange={h} rows={3} />
        </Field>
      </div>
  );
}

function FormRelatorio({ data, onChange }) {
  const h = e => onChange({ ...data, [e.target.name]: e.target.value });

  const setIndicador = (i, field, val) => {
    const inds = data.indicadores.map((ind, idx) => idx === i ? { ...ind, [field]: val } : ind);
    onChange({ ...data, indicadores: inds });
  };
  const addIndicador = () => onChange({ ...data, indicadores: [...data.indicadores, { nome: '', valor: '', resultado: '' }] });
  const removeIndicador = i => onChange({ ...data, indicadores: data.indicadores.filter((_, idx) => idx !== i) });

  return (
      <div className="form-grid">
        <div className="form-section-title">Informações Gerais</div>
        <Field label="Título do Relatório"><input className="in" name="titulo" value={data.titulo} onChange={h} required /></Field>
        <Field label="Período"><input className="in" name="periodo" value={data.periodo} onChange={h} placeholder="ex: Jan/2026 – Mai/2026" required /></Field>
        <Field label="Responsável"><input className="in" name="responsavel" value={data.responsavel} onChange={h} required /></Field>

        <div className="form-section-title">
          Indicadores
          <button type="button" className="btn-add" onClick={addIndicador}>+ Adicionar</button>
        </div>
        {data.indicadores.map((ind, i) => (
            <div className="dynamic-row" key={i}>
              <input className="in" placeholder="Nome do indicador" value={ind.nome} onChange={e => setIndicador(i, 'nome', e.target.value)} />
              <input className="in" placeholder="Valor" value={ind.valor} onChange={e => setIndicador(i, 'valor', e.target.value)} />
              <input className="in" placeholder="Resultado" value={ind.resultado} onChange={e => setIndicador(i, 'resultado', e.target.value)} />
              {data.indicadores.length > 1 && (
                  <button type="button" className="btn-remove" onClick={() => removeIndicador(i)}>✕</button>
              )}
            </div>
        ))}

        <div className="form-section-title">Observações</div>
        <Field label="Observações">
          <textarea className="in in-textarea" name="observacoes" value={data.observacoes} onChange={h} rows={4} />
        </Field>
      </div>
  );
}

function FormNotaFiscal({ data, onChange }) {
  const h = e => onChange({ ...data, [e.target.name]: e.target.value });

  const setItem = (i, field, val) => {
    const itens = data.itens.map((it, idx) => idx === i ? { ...it, [field]: val } : it);
    onChange({ ...data, itens });
  };
  const addItem = () => onChange({ ...data, itens: [...data.itens, { descricao: '', quantidade: 1, valorUnitario: 0 }] });
  const removeItem = i => onChange({ ...data, itens: data.itens.filter((_, idx) => idx !== i) });

  const total = data.itens.reduce((s, it) => s + (Number(it.quantidade) * Number(it.valorUnitario)), 0);

  return (
      <div className="form-grid">
        <div className="form-section-title">Dados da Nota</div>
        <Field label="Número NF"><input className="in" name="numeroNf" value={data.numeroNf} onChange={h} required /></Field>
        <Field label="Data de Emissão"><input className="in" name="dataEmissao" type="date" value={data.dataEmissao} onChange={h} required /></Field>

        <div className="form-section-title">Dados do Cliente</div>
        <Field label="Nome do Cliente"><input className="in" name="nomeCliente" value={data.nomeCliente} onChange={h} required /></Field>
        <Field label="CPF/CNPJ"><input className="in" name="cpfCnpjCliente" value={data.cpfCnpjCliente} onChange={h} required /></Field>
        <Field label="Endereço"><input className="in" name="enderecoCliente" value={data.enderecoCliente} onChange={h} required /></Field>

        <div className="form-section-title">
          Itens
          <button type="button" className="btn-add" onClick={addItem}>+ Adicionar Item</button>
        </div>
        {data.itens.map((it, i) => (
            <div className="dynamic-row" key={i}>
              <input className="in" placeholder="Descrição" value={it.descricao} onChange={e => setItem(i, 'descricao', e.target.value)} />
              <input className="in in-sm" type="number" placeholder="Qtd" min="1" value={it.quantidade} onChange={e => setItem(i, 'quantidade', e.target.value)} />
              <input className="in in-sm" type="number" placeholder="Valor unit. (R$)" step="0.01" value={it.valorUnitario} onChange={e => setItem(i, 'valorUnitario', e.target.value)} />
              {data.itens.length > 1 && (
                  <button type="button" className="btn-remove" onClick={() => removeItem(i)}>✕</button>
              )}
            </div>
        ))}
        <div className="total-bar">Total: <strong>R$ {total.toFixed(2)}</strong></div>
      </div>
  );
}

function FormContrato({ data, onChange }) {
  const h = e => onChange({ ...data, [e.target.name]: e.target.value });

  const setClausula = (i, val) => {
    const clausulas = data.clausulas.map((c, idx) => idx === i ? val : c);
    onChange({ ...data, clausulas });
  };
  const addClausula = () => onChange({ ...data, clausulas: [...data.clausulas, ''] });
  const removeClausula = i => onChange({ ...data, clausulas: data.clausulas.filter((_, idx) => idx !== i) });

  return (
      <div className="form-grid">
        <div className="form-section-title">Identificação</div>
        <Field label="Número do Contrato"><input className="in" name="numeroContrato" value={data.numeroContrato} onChange={h} required /></Field>
        <Field label="Data de Assinatura"><input className="in" name="dataAssinatura" type="date" value={data.dataAssinatura} onChange={h} required /></Field>
        <Field label="Prazo"><input className="in" name="prazo" value={data.prazo} onChange={h} placeholder="ex: 12 meses" required /></Field>

        <div className="form-section-title">Partes Envolvidas</div>
        <Field label="Parte A (Contratante)"><input className="in" name="parteA" value={data.parteA} onChange={h} required /></Field>
        <Field label="Parte B (Contratada)"><input className="in" name="parteB" value={data.parteB} onChange={h} required /></Field>

        <div className="form-section-title">Objeto</div>
        <Field label="Objeto do Contrato">
          <textarea className="in in-textarea" name="objeto" value={data.objeto} onChange={h} rows={3} required />
        </Field>

        <div className="form-section-title">
          Cláusulas
          <button type="button" className="btn-add" onClick={addClausula}>+ Adicionar</button>
        </div>
        {data.clausulas.map((cl, i) => (
            <div className="dynamic-row" key={i}>
              <span className="clausula-num">§ {i + 1}</span>
              <textarea className="in in-textarea" rows={2} value={cl} onChange={e => setClausula(i, e.target.value)} placeholder={`Texto da cláusula ${i + 1}`} />
              {data.clausulas.length > 1 && (
                  <button type="button" className="btn-remove" onClick={() => removeClausula(i)}>✕</button>
              )}
            </div>
        ))}
      </div>
  );
}


function App() {
  const [tipoDoc, setTipoDoc] = useState('RELATORIO');
  const [formato, setFormato] = useState('PDF');
  const [feedback, setFeedback] = useState(null);
  const [loading, setLoading] = useState(false);

  const [relatorioData, setRelatorioData] = useState(initRelatorio);
  const [notaData,      setNotaData]      = useState(initNotaFiscal);
  const [propostaData,  setPropostaData]  = useState(initProposta);
  const [contratoData,  setContratoData]  = useState(initContrato);

  const tipoInfo = TIPOS.find(t => t.key === tipoDoc);

  const buildPayload = () => {
    const base = { tipoDocumento: tipoDoc, formato };
    switch (tipoDoc) {
      case 'RELATORIO':
        return { ...base, relatorioData };
      case 'NOTA_FISCAL':
        return {
          ...base,
          notaFiscalData: {
            ...notaData,
            itens: notaData.itens.map(it => ({
              ...it,
              quantidade: parseInt(it.quantidade),
              valorUnitario: parseFloat(it.valorUnitario)
            }))
          }
        };
      case 'PROPOSTA':
        return {
          ...base,
          propostaData: {
            ...propostaData,
            itens: propostaData.itens.map(it => ({ ...it, valor: parseFloat(it.valor) }))
          }
        };
      case 'CONTRATO':
        return { ...base, contratoData };
      default:
        return base;
    }
  };


  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setFeedback(null);

    try {
      const response = await fetch('http://localhost:8080/api/documentos/gerar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(buildPayload()),
      });

      if (!response.ok) throw new Error('Erro ao gerar documento.');

      const blob = await response.blob();
      const ext  = formato.toLowerCase();
      const url  = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `${tipoDoc.toLowerCase()}.${ext}`);
      document.body.appendChild(link);
      link.click();
      link.parentNode.removeChild(link);

      setFeedback({ type: 'ok', msg: `Documento gerado com sucesso! (${formato})` });
    } catch {
      setFeedback({ type: 'err', msg: 'Falha ao gerar o documento. Verifique o servidor.' });
    } finally {
      setLoading(false);
    }
  };




  return (
      <>
        <section id="center">

          <div className="main">
            {/* ── Seletor de tipo de documento ── */}
            <div className="tipo-selector">
              {TIPOS.map(t => (
                  <button
                      key={t.key}
                      type="button"
                      className={`tipo-btn ${tipoDoc === t.key ? 'active' : ''}`}
                      onClick={() => { setTipoDoc(t.key); setFeedback(null); }}
                  >
                    {t.label}
                  </button>
              ))}
            </div>

            {/* Seletor de formato */}
                <div className="formato-selector">
                  {/** 
                  <span className="formato-label-text">Formato:</span> */}
                  {FORMATOS.map(f => (
                      <button
                          key={f.key}
                          type="button"
                          className={`formato-btn ${formato === f.key ? 'active' : ''}`}
                          style={formato === f.key ? { background: f.color, borderColor: f.color } : {}}
                          onClick={() => setFormato(f.key)}
                      >
                        {f.label}
                      </button>
                  ))}
                </div>

            {/* ── Card principal ── */}
            <form className="doc-card" onSubmit={handleSubmit}>
              {/* Cabeçalho do card */}
              <div className="card-header">
                <div className="card-title">
                  {tipoInfo.label}
                </div>

                
              </div>

              {/* Corpo com formulário dinâmico */}
              <div className="card-body">
                {tipoDoc === 'RELATORIO'   && <FormRelatorio data={relatorioData} onChange={setRelatorioData} />}
                {tipoDoc === 'NOTA_FISCAL' && <FormNotaFiscal data={notaData}     onChange={setNotaData} />}
                {tipoDoc === 'PROPOSTA'    && <FormProposta  data={propostaData}  onChange={setPropostaData} />}
                {tipoDoc === 'CONTRATO'    && <FormContrato  data={contratoData}  onChange={setContratoData} />}
              </div>

              {/* Footer do card */}
              <div className="card-footer">
                {feedback && (
                    <div className={`feedback ${feedback.type}`}>{feedback.msg}</div>
                )}
                <button type="submit" className="formBTN" disabled={loading}>
                  {loading
                      ? <span className="loading-spinner" />
                      : `Gerar ${tipoInfo.label} em ${formato}`
                  }
                </button>
              </div>
            </form>
          </div>

        </section>
      </>
  )
}

export default App;

