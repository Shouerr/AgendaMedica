package com.example.agendamedica.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agendamedica.viewmodel.CitaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitaScreen(
    navController: NavController,
    viewModel: CitaViewModel = viewModel()
) {
    // Mapa de provincias y hospitales
    val hospitalesPorProvincia = mapOf(
        "San José" to listOf(
            "Centro Médico Miopiel", "Centro Médico Momentum Escazú", "Centro Médico Mün", "Centro Médico Nacional", "Centro Médico Nativa",
            "Centro Médico Navas", "Centro Médico Nazareth", "Centro Médico Nuestra Señora de los Ángeles", "Centro Médico Nunciatura",
            "Centro Médico Nutricional y Deportivo Dra. Gabriela Chacón", "Centro Médico Oncológico Dr. Leonardo Madriz",
            "Centro Médico Palmares", "Centro Médico Peniel", "Centro Médico Pinares", "Centro Médico Pro Salud",
            "Centro Médico Prosalud de Centroamérica", "Centro Médico Provida", "Centro Médico Puriscal", "Centro Médico Quirúrgico",
            "Centro Médico Quirúrgico San Pancracio", "Centro Médico Regional del INS Filadelfia", "Centro Médico Sagrado Corazón",
            "Centro Médico Salud y Vida", "Centro Médico Salvus", "Centro Médico San Antonio", "Centro Médico San Carlos",
            "Centro Médico San Francisco", "Centro Médico San Francisco Javier", "Centro Médico San Gabriel",
            "Centro Médico San Isidro", "Centro Médico San Joaquín", "Centro Médico San Joaquín de Flores", "Centro Médico San José",
            "Centro Médico San Juan Bosco", "Centro Médico San Juan de Dios", "Centro Médico San Marcos de Tarrazú",
            "Centro Médico San Miguel Arcángel", "Centro Médico San Onofre", "Centro Médico San Rafael", "Centro Médico San Vicente",
            "Centro Médico Santa Ana", "Centro Médico Santa Clara", "Centro Médico Santa Elena", "Centro Médico Santa María",
            "Centro Médico Santa Mónica", "Centro Médico Santa Paula", "Centro Médico Santa Sofía", "Centro Médico Santiago Apóstol",
            "Centro Médico Saprissa", "Centro Médico Sarchí", "Centro Médico Sinaí, Tibás", "Centro Médico Sirius", "Centro Médico Skorpio",
            "Centro Médico Sunnu", "Centro Médico Terranova", "Centro Médico Torre Fuerte", "Centro Médico UyM", "Centro Médico Vargas",
            "Centro Médico Vesalio", "Centro Médico Victoria", "Centro Médico Vida Sana", "Centro Médico Villa Hermosa",
            "Centro Médico VillaMed", "Centro Médico Vital", "Centro Médico Yireh", "Centro Médico Zarcero", "Centro Médico Zeledón, Bribrí",
            "Centro Médico Zeledón, Puerto Viejo", "Centro Médico para la Mujer", "Centro Médico y Cirugía Menor San José",
            "Centro Médico y Estético Ikigai", "Centro Médico y Estético Nosara", "Centro Nacional de Rehabilitación Dr. Humberto Araya Rojas",
            "Clínica Andar C.R.", "Clínica Hospital Santa Catalina", "Clínica Zahha", "Hospital CIMA San José", "Hospital Clínica Bíblica",
            "Hospital Clínica Santa Rita", "Hospital Clínica Unibe", "Hospital Cristiano Jerusalem", "Hospital La Católica",
            "Hospital Metropolitano", "Hospital México", "Hospital Nacional Psiquiátrico Manuel Antonio Chapuí y Torres",
            "Hospital Nacional de Geriatría y Gerontología Dr. Raúl Blanco Cervantes", "Hospital Nacional de Niños Carlos Sáenz Herrera",
            "Hospital Rafael Ángel Calderón Guardia", "Hospital San Juan de Dios", "Hospital de las Mujeres Dr. Adolfo Carit Eva"
        ),

        "Alajuela" to listOf(
            "Blue Medical, Sede Alajuela", "Centro Médico Alfaro", "Centro Médico Integrativo La Caridad del Cobre", "Centro Médico Jackson Phoenix",
            "Centro Médico Jackson's Memorial", "Centro Médico del Parque", "Centro de Especialidades Médicas DaLuz", "Clínica Dra. Laura Ureña",
            "Clínica Dra. Marta García Palomo", "Clínica Helénica", "Clínica Jericó", "Clínica Kibina", "Clínica Los Ángeles, San Ramón",
            "Clínica Lux", "Clínica Mahelet - Grecia", "Clínica Maja", "Clínica Médica Eidita", "Hospital Carlos Luis Valverde Vega",
            "Hospital San Francisco de Asís", "Hospital San Rafael", "Hospital de Los Chiles", "Hospital de San Carlos", "Hospital de Upala"
        ),

        "Cartago" to listOf(
            "Centro Médico Jerusem", "Clínica Calle Real", "Clínica Médico Quirúrgica de Cartago ​", "Clínica Sin Fronteras ​",
            "Hospital Clínica Señora de los Ángeles ​", "Hospital Dr. Maximiliano Peralta Jiménez ​", "Hospital Metropolitano, Sede Cartago ​",
            "Hospital Metropolitano, Sede Ofiplaza Boulevard Cartago, El Molino", "Hospital Universal ​", "Hospital William Allen Taylor ​"
        ),

        "Heredia" to listOf(
            "Blue Medical, Sede Cariari", "Clínica Cedcas", "Clínica LHS, Sede AFZ", "Clínica LHS, Sede Belén", "Clínica Murano", "Clínica Santa Fe",
            "Hospital Metropolitano, Sede Heredia", "Hospital San Vicente de Paúl", "ROCH Clínica Médica"
        ),

        "Guanacaste" to listOf(
            "Clínica 25 de Julio", "Clínica Monte de la Cruz", "Coco Medical Center", "Guanacaste Medical Center", "Hospital Clínica San Rafael Arcángel",
            "Hospital Enrique Baltodano Briceño", "Hospital La Anexión", "Hospital Metropolitano, Sede Liberia"
        ),

        "Puntarenas" to listOf(
            "Clínica Montespino 24h (El Roble)", "Clínica Médica Algara Del Roble (El Roble)", "Clínica Médica Dr. Lostalo (Barranca)",
            "Clínica San Rafael (Puntarenas)", "Clínica Santa Martha (Puntarenas)", "Hospital Dr. Tomás Casas Casajús (Puerto Cortés)",
            "Hospital Manuel Mora Valverde (Golfito)", "Hospital Max Terán Valls (Quepos)", "Hospital Monseñor Víctor Manuel Sanabria Martínez (Puntarenas)",
            "Hospital de Ciudad Neily (Ciudad Neily)", "Hospital de San Vito (San Vito)", "Santa Teresa Medical Center (Santa Teresa)"
        ),

        "Limón" to listOf(
            "Centro Médico del Caribe (CMDC) (Limón)", "Clínica Atlántica (Guápiles)", "Clínica Santa Teresa (Limón)", "Clínica Somedica (Limón)​",
            "Clínica Vida Caribe (Limón)", "Hospital Dr. Tony Facio Castro (Limón)​", "Hospital de Guápiles (Guápiles)"
        )
    )

    // Estados locales del formulario
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var provincia by remember { mutableStateOf("") }
    var hospital by remember { mutableStateOf("") }
    var motivo by remember { mutableStateOf("") }

    // Estados de los dropdowns
    var provinciaExpanded by remember { mutableStateOf(false) }
    var hospitalExpanded by remember { mutableStateOf(false) }

    val hospitales = hospitalesPorProvincia[provincia] ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Agendar Cita", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha (DD/MM/AAAA)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = hora,
            onValueChange = { hora = it },
            label = { Text("Hora (HH:MM)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🔽 Selector de Provincia
        ExposedDropdownMenuBox(
            expanded = provinciaExpanded,
            onExpandedChange = { provinciaExpanded = !provinciaExpanded }
        ) {
            OutlinedTextField(
                value = provincia,
                onValueChange = {},
                readOnly = true,
                label = { Text("Provincia") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = provinciaExpanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            DropdownMenu(
                expanded = provinciaExpanded,
                onDismissRequest = { provinciaExpanded = false }
            ) {
                hospitalesPorProvincia.keys.forEach { prov ->
                    DropdownMenuItem(
                        text = { Text(prov) },
                        onClick = {
                            provincia = prov
                            hospital = ""
                            provinciaExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🔽 Selector de Hospital (filtrado)
        if (provincia.isNotEmpty()) {
            ExposedDropdownMenuBox(
                expanded = hospitalExpanded,
                onExpandedChange = { hospitalExpanded = !hospitalExpanded }
            ) {
                OutlinedTextField(
                    value = hospital,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Hospital") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = hospitalExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                DropdownMenu(
                    expanded = hospitalExpanded,
                    onDismissRequest = { hospitalExpanded = false }
                ) {
                    hospitales.forEach { hosp ->
                        DropdownMenuItem(
                            text = { Text(hosp) },
                            onClick = {
                                hospital = hosp
                                hospitalExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = motivo,
            onValueChange = { motivo = it },
            label = { Text("Motivo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.agregarCita(
                    fecha = fecha,
                    hora = hora,
                    provincia = provincia,
                    hospital = hospital,
                    medico = "Sin asignar",
                    motivo = motivo
                )
                navController.popBackStack()
            },
            enabled = fecha.isNotBlank() && hora.isNotBlank()
                    && provincia.isNotBlank() && hospital.isNotBlank()
        ) {
            Text("Guardar Cita")
        }
    }
}