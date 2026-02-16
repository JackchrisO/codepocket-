package com.example.codepocket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class Dica(val id: Int, val nome: String, val explicacao: String, val codigo: String, val nivel: String)

fun gerarDicas(): List<Dica> {
    val lista = mutableListOf<Dica>()
    var id = 1

    fun add(nome: String, explicacao: String, codigo: String, nivel: String) {
        lista.add(Dica(id++, nome, explicacao, codigo, nivel))
    }

    // ================= BASICO =================
    add("Val vs Var", "val imutável, var mutável", "val nome = \"Jack\"\nvar idade = 20", "BASICO")
    add("Funções", "Funções definem comportamento", "fun soma(a: Int, b: Int) = a + b", "BASICO")
    add("If/Else", "Condicional simples", "if(cond) {...} else {...}", "BASICO")
    add("When", "Condicional múltipla", "when(x) { 1 -> ...; else -> ... }", "BASICO")
    add("Loops", "Repetição de código", "for(i in 1..5) {...}", "BASICO")
    add("Listas", "Armazenam múltiplos valores", "val lista = listOf(1,2,3)", "BASICO")
    add("Mapas", "Chave -> valor", "val map = mapOf(\"a\" to 1, \"b\" to 2)", "BASICO")
    add("Null Safety", "Evita NullPointer", "var x: String? = null", "BASICO")
    add("Smart Cast", "Reconhece tipo automaticamente", "if(x is String) x.length", "BASICO")
    add("Extension", "Funções extras para classes", "fun String.hello() = \"Hello\"", "BASICO")
    add("For Each", "Itera sobre coleções", "lista.forEach { println(it) }", "BASICO")
    add("While", "Loop condicional", "while(x < 5) { x++ }", "BASICO")
    add("Operador Elvis", "Valor padrão se null", "val nome = usuario ?: \"Desconhecido\"", "BASICO")
    add("Comentários", "Documenta código", "// Comentário simples", "BASICO")

    // ================= INTERMEDIARIO =================
    add("Data Class", "Classe automática para dados", "data class Usuario(val nome: String)", "INTERMEDIARIO")
    add("Função de extensão", "Adiciona função a classe existente", "fun String.gritar() = this.uppercase()", "INTERMEDIARIO")
    add("Lambda", "Função anônima", "val soma = { a: Int, b: Int -> a + b }", "INTERMEDIARIO")
    add("Filter", "Filtra lista", "lista.filter { it > 5 }", "INTERMEDIARIO")
    add("Map", "Transforma elementos", "lista.map { it * 2 }", "INTERMEDIARIO")
    add("Try Catch", "Trata erros", "try { x/0 } catch(e: Exception){}", "INTERMEDIARIO")
    add("Lazy", "Inicializa quando necessário", "val nome by lazy { \"Jack\" }", "INTERMEDIARIO")
    add("Scope Function - let", "Executa bloco com objeto", "usuario?.let { println(it.nome) }", "INTERMEDIARIO")
    add("Scope Function - apply", "Configura objeto", "val user = Usuario(\"Ana\").apply { println(nome) }", "INTERMEDIARIO")
    add("Companion Object", "Membros estáticos", "companion object { const val PI = 3.14 }", "INTERMEDIARIO")
    add("Enum Class", "Conjunto fixo de valores", "enum class Dia { SEG, TER }", "INTERMEDIARIO")
    add("Sealed Class", "Herança restrita", "sealed class Resultado", "INTERMEDIARIO")
    add("Data Manipulation", "Destructuring", "val (nome) = usuario", "INTERMEDIARIO")
    add("Init Block", "Executa na criação do objeto", "init { println(\"Criado\") }", "INTERMEDIARIO")
    add("Primary Constructor", "Construtor na declaração da classe", "class Pessoa(val nome: String)", "INTERMEDIARIO")

    // ================= AVANCADO =================
    add("Coroutines", "Execução assíncrona leve", "launch { delay(1000) }", "AVANCADO")
    add("Suspend Function", "Função que pode pausar", "suspend fun carregar() {}", "AVANCADO")
    add("Flow", "Fluxo reativo de dados", "flow { emit(1) }", "AVANCADO")
    add("StateFlow", "Estado observável", "val estado = MutableStateFlow(0)", "AVANCADO")
    add("ViewModel", "Gerencia estado da UI", "class MainViewModel: ViewModel()", "AVANCADO")
    add("Remember Compose", "Guarda estado na composição", "val contador = remember { mutableStateOf(0) }", "AVANCADO")
    add("LaunchedEffect", "Executa efeito colateral", "LaunchedEffect(Unit) { println(\"Start\") }", "AVANCADO")
    add("Navigation Compose", "Gerencia telas", "NavHost(navController, startDestination=\"home\")", "AVANCADO")
    add("Dependency Injection", "Injeção de dependência", "@Inject lateinit var repo: Repo", "AVANCADO")
    add("Clean Architecture", "Separação de camadas", "data -> domain -> presentation", "AVANCADO")
    add("SOLID - SRP", "Responsabilidade única", "Uma classe = uma função clara", "AVANCADO")
    add("Repository Pattern", "Intermedia dados", "class UserRepository", "AVANCADO")
    add("Unit Test", "Testa partes isoladas", "@Test fun testeSoma() {}", "AVANCADO")
    add("Compose State Hoisting", "Elevar estado para pai", "MyScreen(state, onEvent)", "AVANCADO")
    add("Performance - Recomposition", "Evitar recomposições desnecessárias", "remember e derivedStateOf", "AVANCADO")

    return lista
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "home") {
                composable("home") { HomeScreen(navController) }
                composable("dicas") { DicasScreen(navController) }
                composable("sheets") { SheetsMenu(navController) }
                composable("sheets_list/{nivel}") { backStackEntry ->
                    val nivel = backStackEntry.arguments?.getString("nivel") ?: "BASICO"
                    SheetsListScreen(navController, nivel)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "CodePocket",
                fontSize = 36.sp,
                color = Color(0xFF00FF00),
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = { navController.navigate("dicas") }) {
                Text("Dicas Kotlin", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController.navigate("sheets") }) {
                Text("Sheets", color = Color.Black)
            }
        }
    }
}

@Composable
fun DicasScreen(navController: NavHostController) {
    val dicas = gerarDicas()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        dicas.forEach { dica ->
            Text(dica.nome, fontFamily = FontFamily.Monospace, fontSize = 20.sp, color = Color(0xFF00FF00))
            Text(dica.explicacao, fontFamily = FontFamily.Monospace, fontSize = 16.sp, color = Color.LightGray)
            Text(dica.codigo, fontFamily = FontFamily.Monospace, fontSize = 16.sp, color = Color.Cyan)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun SheetsMenu(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Escolha o nível", color = Color(0xFF00FF00), fontFamily = FontFamily.Monospace, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(30.dp))
            Button(onClick = { navController.navigate("sheets_list/BASICO") }) { Text("Básico", color = Color.Black) }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController.navigate("sheets_list/INTERMEDIARIO") }) { Text("Intermediário", color = Color.Black) }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController.navigate("sheets_list/AVANCADO") }) { Text("Avançado", color = Color.Black) }
        }
    }
}

@Composable
fun SheetsListScreen(navController: NavHostController, nivel: String) {
    val dicas = gerarDicas().filter { it.nivel == nivel }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(nivel, fontFamily = FontFamily.Monospace, fontSize = 24.sp, color = Color(0xFF00FF00))
        Spacer(modifier = Modifier.height(16.dp))
        dicas.forEach { dica ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.DarkGray)
                    .clickable { /* opcional: abrir detalhamento */ }
                    .padding(8.dp)
            ) {
                Text(dica.nome, fontFamily = FontFamily.Monospace, fontSize = 20.sp, color = Color.Green)
                Text(dica.explicacao, fontFamily = FontFamily.Monospace, fontSize = 16.sp, color = Color.Magenta)
                Text(dica.codigo, fontFamily = FontFamily.Monospace, fontSize = 16.sp, color = Color.Cyan)
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}