package hu.zsof.restaurantappjetpacknew.ui.common.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    text: String,
    onChecked: (Boolean) -> Unit = {},
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    shouldShowIcon: Boolean = true
) {
    val shape = RoundedCornerShape(12.dp)
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(
                vertical = 6.dp,
                horizontal = 4.dp,
            )
            .border(
                width = 1.dp,
                color = if (isSelected) selectedColor else LightGray,
                shape = shape,
            )
            .background(
                color = if (isSelected) selectedColor else Transparent,
                shape = shape,
            )
            .clip(shape = shape)
            .clickable {
                onChecked(!isSelected)
            }
            .padding(4.dp),
    ) {
        if (isSelected && shouldShowIcon) {
            Icon(
                imageVector = Icons.Filled.Done,
                tint = DarkGray,
                contentDescription = null,
            )
        }
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview
@Composable
fun TextChipPreview(){
    TextChip(isSelected = false, text = "Teszt chip")
}