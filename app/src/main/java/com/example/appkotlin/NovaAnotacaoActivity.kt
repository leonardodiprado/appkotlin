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

class NovaAnotacaoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(this) // ✅ Corrigido aqui
        val dao = db.anotacaoDao()

        setContent {
            AppKotlinTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    FormularioNovaAnotacao(dao = dao, onSalvar = { finish() })
                }
            }
        }
    }
}

@Composable
fun FormularioNovaAnotacao(dao: AnotacaoDao, onSalvar: () -> Unit) {
    var titulo by remember { mutableStateOf("") }
    var conteudo by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Nova Anotação", style = MaterialTheme.typography.headlineSmall)
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
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    if (titulo.isNotBlank() && conteudo.isNotBlank()) {
                        dao.inserir(Anotacao(titulo = titulo, conteudo = conteudo))
                        Toast.makeText(context, "Anotação salva!", Toast.LENGTH_SHORT).show()
                        onSalvar()
                    } else {
                        Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Salvar")
            }
        }
    }
}

