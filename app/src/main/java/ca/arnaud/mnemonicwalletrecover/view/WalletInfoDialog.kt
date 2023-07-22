package ca.arnaud.mnemonicwalletrecover.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ca.arnaud.MnemonicWalletRecover.R
import ca.arnaud.mnemonicwalletrecover.model.LoadingButtonModel
import ca.arnaud.mnemonicwalletrecover.model.WalletInfoDialogModel
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverAppTheme
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverTheme

@Composable
fun WalletInfoDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onCopyPrivateKeyClick: () -> Unit,
    model: WalletInfoDialogModel,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(12.dp),
            backgroundColor = MnemonicWalletRecoverTheme.colors.background,
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                val keyResultModifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .background(
                        MnemonicWalletRecoverTheme.colors.secondaryBackground,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(5.dp)

                Text(
                    style = MnemonicWalletRecoverTheme.typography.h1,
                    text = model.title,
                    color = MnemonicWalletRecoverTheme.colors.primaryLabel
                )

                Text(
                    modifier = Modifier.padding(top = 15.dp),
                    style = MnemonicWalletRecoverTheme.typography.h2,
                    text = stringResource(R.string.public_key_title),
                    color = MnemonicWalletRecoverTheme.colors.primaryLabel
                )

                Text(
                    modifier = keyResultModifier,
                    style = MnemonicWalletRecoverTheme.typography.body1,
                    text = model.fullPublicKey,
                    color = MnemonicWalletRecoverTheme.colors.primaryLabel
                )

                Text(
                    modifier = Modifier.padding(top = 15.dp),
                    style = MnemonicWalletRecoverTheme.typography.h2,
                    text = stringResource(R.string.private_key_title),
                    color = MnemonicWalletRecoverTheme.colors.primaryLabel
                )

                Text(
                    modifier = keyResultModifier,
                    style = MnemonicWalletRecoverTheme.typography.body1,
                    text = model.formattedPrivateKey,
                    color = MnemonicWalletRecoverTheme.colors.primaryLabel
                )

                LoadingButton(
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(MnemonicWalletRecoverTheme.dimensions.buttonHeight),
                    model = model.copyButton,
                    onClick = onCopyPrivateKeyClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WalletInfoDialogDefaultPreview() {
    MnemonicWalletRecoverAppTheme {
        WalletInfoDialog(
            model = WalletInfoDialogModel(
                "Wallet", "o14n132o123no3", "32981312932",
                LoadingButtonModel("Button", false)
            ),
            onDismiss = {},
            onCopyPrivateKeyClick = {},
        )
    }
}