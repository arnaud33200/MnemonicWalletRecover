package ca.arnaud.mnemonicwalletrecover.view

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.arnaud.mnemonicwalletrecover.model.LoadingButtonModel
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverAppTheme
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverTheme

@Composable
fun LoadingButton(
    modifier: Modifier,
    onClick: () -> Unit,
    model: LoadingButtonModel
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = !model.isLoading,
    ) {
        if (model.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(20.dp, 20.dp),
                strokeWidth = 3.dp
            )
        }

        Text(
            text = model.title,
            textAlign = TextAlign.Center,
            style = MnemonicWalletRecoverTheme.typography.button1,
            color = when (model.isLoading) {
                true -> MnemonicWalletRecoverTheme.colors.labelOnPrimary
                false -> MnemonicWalletRecoverTheme.colors.onSecondary
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoadingButtonIsNotLoading() {
    MnemonicWalletRecoverAppTheme {
        LoadingButton(
            modifier = Modifier
                .height(50.dp),
            model = LoadingButtonModel(
                "Create Wallet",
                isLoading = false
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoadingButtonIsLoading() {
    MnemonicWalletRecoverAppTheme(true) {
        LoadingButton(
            modifier = Modifier
                .height(50.dp),
            model = LoadingButtonModel(
                "Loading...",
                isLoading = true
            ),
            onClick = {}
        )
    }
}

