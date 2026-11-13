'use strict';
const fs = require('node:fs');
const path = require('node:path');

let raw = '';
process.stdin.setEncoding('utf8');
process.stdin.on('data', chunk => { raw += chunk; });
process.stdin.on('end', () => {
  let payload;
  try { payload = JSON.parse(raw || '{}'); }
  catch {
    console.error('[access-policy] No se pudo parsear el evento JSON.');
    process.exit(2);
  }

  const tool = String(payload.tool_name || '');
  const input = payload.tool_input || {};
  const file = input.file_path || input.path || input.file || '';
  if (typeof file !== 'string' || !file.trim()) return;

  const base = payload.cwd || process.cwd();
  const absolute = path.resolve(base, file);
  // Para archivos existentes, resolver symlinks para que el archivo real sea el controlado.
  let resolved = absolute;
  try { resolved = fs.realpathSync(absolute); } catch { /* Todavía no existe. */ }
  const normalized = resolved.replaceAll('\\', '/');
  const filename = path.basename(normalized);
  const isRead = tool === 'Read';
  const isWrite = ['Edit', 'Write', 'MultiEdit'].includes(tool);

  const envSecret = /^\.env(?:\..+)?$/i.test(filename) && filename !== '.env.example';
  const keySecret = /\.(?:pem|key)$/i.test(filename);
  const secret = envSecret || keySecret;
  const protectedConfig = /\/src\/main\/resources\/application\.(?:yaml|yml|properties)$/i.test(normalized);
  if (secret && (isRead || isWrite)) {
    console.error(`[access-policy] BLOQUEADO: ${tool} sobre archivo sensible (${filename}). El acceso a archivos sensibles está restringido.`);
    process.exit(2);
  }
  if (protectedConfig && isWrite) {
    console.error('[access-policy] BLOQUEADO: configuración base protegida. Utilizá src/main/resources/application-local.yaml para la configuración local.');
    process.exit(2);
  }
});
