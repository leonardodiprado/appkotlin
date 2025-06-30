package com.example.appkotlin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.appkotlin.ui.theme.AppKotlinTheme

class EditarAnotacaoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val anotacaoId = intent.getIntExtra("id", -1)
        val db = AppDatabase.getInstance(this)
        val dao = db.anotacaoDao()
        val anotacao = dao.listarTodas().find { it.id == anotacaoId }

        setContent {
            AppKotlinTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    if (anotacao != null) {
                        FormularioEditarAnotacao(
                            anotacao = anotacao,
                            dao = dao,
                            onFinalizar = { finish() }
                        )
                    } else {
                        Text("Anotação não encontrada.")
                    }
                }
            }
        }
    }
}

@Composable
fun FormularioEditarAnotacao(anotacao: Anotacao, dao: AnotacaoDao, onFinalizar: () -> Unit) {
    var titulo by remember { mutableStateOf(anotacao.titulo) }
    var conteudo by remember { mutableStateOf(anotacao.conteudo) }
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Editar Anotação", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = conteudo,
            onValueChange = { conteudo = it },
            label = { Text("Conteúdo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                dao.deletar(anotacao)
                Toast.makeText(context, "Anotação excluída", Toast.LENGTH_SHORT).show()
                onFinalizar()
            }) {
                Text("Excluir")
            }

            Button(onClick = {
                if (titulo.isNotBlank() && conteudo.isNotBlank()) {
                    dao.atualizar(anotacao.copy(titulo = titulo, conteudo = conteudo))
                    Toast.makeText(context, "Alterações salvas", Toast.LENGTH_SHORT).show()
                    onFinalizar()
                } else {
                    Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Salvar")
            }
        }
    }
}
