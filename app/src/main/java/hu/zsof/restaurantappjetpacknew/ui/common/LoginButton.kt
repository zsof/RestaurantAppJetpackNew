package hu.zsof.restaurantappjetpacknew.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginButton(
    onClick: () -> Unit,
    text: String,
) {
    val shape =
        RoundedCornerShape(20.dp)
    Button(
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(16.dp),
        onClick = onClick,
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp,
        )
    }
}
