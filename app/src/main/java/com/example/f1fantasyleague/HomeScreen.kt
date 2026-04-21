package com.example.f1fantasyleague

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
	val cardShape = RoundedCornerShape(30.dp)
	var passcode by remember { mutableStateOf("") }
	val cardBorderColor = Color(0xFF353535)
	val cardContainerColor = Color(0xFF1E1E1E)
	val tableRowColor = Color(0xFF272727)
	val tableCellStartPadding = 12.dp
	val tableDividerWidth = 0.8.dp

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(Color(0xFF161616)),
		contentAlignment = Alignment.TopCenter
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.verticalScroll(rememberScrollState())
				.padding(top = 20.dp, bottom = 20.dp),
			verticalArrangement = Arrangement.spacedBy(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Card(
				modifier = Modifier
					.padding(horizontal = 20.dp)
					.fillMaxWidth(),
				shape = cardShape,
				colors = CardDefaults.cardColors(containerColor = cardContainerColor),
				border = BorderStroke(0.8.dp, cardBorderColor),
				elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
			) {
				Column(
					horizontalAlignment = Alignment.CenterHorizontally,
				) {
					Box(
						modifier = Modifier
							.fillMaxWidth()
							.height(90.dp)
							.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
							.background(Color(0xFFC62828)),
						contentAlignment = Alignment.Center
					) {
						Text(
							text = "Miami Grand Prix",
							modifier = Modifier.padding(horizontal = 24.dp),
							color = Color(0xFFF2F2F2),
							style = MaterialTheme.typography.headlineMedium,
							fontWeight = FontWeight.Bold
						)
					}

					Column(
						modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
						verticalArrangement = Arrangement.spacedBy(10.dp),
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Text(
							text = "Race 4/22",
							color = Color(0xFFF2F2F2),
							style = MaterialTheme.typography.headlineSmall,
							fontWeight = FontWeight.SemiBold
						)

						Text(
							text = "02/05, 22:00",
							color = Color(0xFFB6B6B6),
							style = MaterialTheme.typography.headlineSmall
						)

						Text(
							text = "11d 10h 32m 20s",
							color = Color(0xFFB6B6B6),
							style = MaterialTheme.typography.headlineSmall
						)
					}
				}
			}

			Card(
				modifier = Modifier
					.padding(horizontal = 20.dp)
					.fillMaxWidth(),
				shape = cardShape,
				colors = CardDefaults.cardColors(containerColor = cardContainerColor),
				border = BorderStroke(0.8.dp, cardBorderColor),
				elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
			) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(Color(0xFFC62828)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Make a guess",
                        modifier = Modifier.padding(horizontal = 24.dp),
                        color = Color(0xFFF2F2F2),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
				Column(
					modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
					verticalArrangement = Arrangement.spacedBy(12.dp),
					horizontalAlignment = Alignment.CenterHorizontally
				) {
                    	Text(
						text = "Your Passcode",
						color = Color(0xFFB6B6B6),
						style = MaterialTheme.typography.headlineSmall
					)

					OutlinedTextField(
						value = passcode,
						onValueChange = { passcode = it },
						modifier = Modifier.fillMaxWidth(),
						singleLine = true,
						visualTransformation = PasswordVisualTransformation()
					)

					Button(
						onClick = { },
						colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828))
					) {
						Text(
                            text = "Check",
                            color = Color(0xFFF2F2F2),
                            style = MaterialTheme.typography.headlineSmall
                        )
					}
				}
			}

			Card(
				modifier = Modifier
					.padding(horizontal = 20.dp)
					.fillMaxWidth(),
				shape = cardShape,
				colors = CardDefaults.cardColors(containerColor = cardContainerColor),
				border = BorderStroke(0.8.dp, cardBorderColor),
				elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
			) {
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.height(90.dp)
						.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
						.background(Color(0xFFC62828)),
					contentAlignment = Alignment.Center
				) {
					Text(
						text = "Mystery Guess Table",
						modifier = Modifier.padding(horizontal = 24.dp),
						color = Color(0xFFF2F2F2),
						style = MaterialTheme.typography.headlineMedium,
						fontWeight = FontWeight.Bold
					)
				}

				Column(
					modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
					verticalArrangement = Arrangement.spacedBy(12.dp),
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					Text(
						text = "Mystery Guesses",
						color = Color(0xFFF2F2F2),
						style = MaterialTheme.typography.headlineSmall,
						fontWeight = FontWeight.SemiBold
					)

					Column(modifier = Modifier.fillMaxWidth()) {
						Row(
							modifier = Modifier
								.fillMaxWidth()
								.height(IntrinsicSize.Min)
								.background(tableRowColor)
								.border(BorderStroke(0.8.dp, cardBorderColor))
						) {
							Text(
								text = "R",
								modifier = Modifier
									.weight(1f)
									.padding(start = tableCellStartPadding, top = 10.dp, bottom = 10.dp),
								color = Color(0xFFF2F2F2),
								style = MaterialTheme.typography.headlineSmall,
								textAlign = TextAlign.Center,
								fontWeight = FontWeight.Bold
							)
							Box(
								modifier = Modifier
									.fillMaxHeight()
									.padding(vertical = 2.dp)
									.width(tableDividerWidth)
									.background(cardBorderColor)
							)
							Text(
								text = "Guess",
								modifier = Modifier
									.weight(2f)
									.padding(start = tableCellStartPadding, top = 10.dp, bottom = 10.dp),
								color = Color(0xFFF2F2F2),
								style = MaterialTheme.typography.headlineSmall,
								textAlign = TextAlign.Center,
								fontWeight = FontWeight.Bold
							)
						}

						for (round in 1..3) {
							Row(
								modifier = Modifier
									.fillMaxWidth()
									.height(IntrinsicSize.Min)
									.background(tableRowColor)
									.border(BorderStroke(0.8.dp, cardBorderColor))
							) {
								Text(
									text = round.toString(),
									modifier = Modifier
										.weight(1f)
										.padding(start = tableCellStartPadding, top = 10.dp, bottom = 10.dp),
									color = Color(0xFFB6B6B6),
									style = MaterialTheme.typography.headlineSmall,
									textAlign = TextAlign.Center
								)
								Box(
									modifier = Modifier
										.fillMaxHeight()
										.padding(vertical = 2.dp)
										.width(tableDividerWidth)
										.background(cardBorderColor)
								)
								Text(
									text = "#",
									modifier = Modifier
										.weight(2f)
										.padding(start = tableCellStartPadding, top = 10.dp, bottom = 10.dp),
									color = Color(0xFFB6B6B6),
									style = MaterialTheme.typography.headlineSmall
								)
							}
						}
					}
				}
			}
		}
	}
}

@Preview(showBackground = true, backgroundColor = 0xFF161616)
@Composable
private fun HomeScreenPreview() {
	HomeScreen()
}
