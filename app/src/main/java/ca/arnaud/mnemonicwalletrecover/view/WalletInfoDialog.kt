package ca.arnaud.mnemonicwalletrecover.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ca.arnaud.MnemonicWalletRecover.R
import ca.arnaud.mnemonicwalletrecover.model.LoadingButtonModel
import ca.arnaud.mnemonicwalletrecover.model.PrivateKeyMode
import ca.arnaud.mnemonicwalletrecover.model.WalletInfoDialogModel
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverAppTheme
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverTheme

interface WalletInfoDialogCallback {

    fun onDismiss()

    fun onCopyPrivateKeyClick()

    fun onPrivateKeyModeClick()
}

@Composable
fun WalletInfoDialog(
    modifier: Modifier = Modifier,
    callback: WalletInfoDialogCallback,
    model: WalletInfoDialogModel,
) {
    key(model.privateKeyMode) { // TODO - not ideal but it force adapt height
        Dialog(
            onDismissRequest = callback::onDismiss
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

                    PrivateKeyRow(
                        modifier = keyResultModifier,
                        modeClick = callback::onPrivateKeyModeClick,
                        text = model.formattedPrivateKey,
                        mode = model.privateKeyMode,
                    )

                    LoadingButton(
                        modifier = Modifier
                            .padding(top = 35.dp)
                            .height(MnemonicWalletRecoverTheme.dimensions.buttonHeight),
                        model = { model.copyButton },
                        onClick = callback::onCopyPrivateKeyClick
                    )
                }
            }
        }
    }
}

@Composable
private fun PrivateKeyRow(
    modifier: Modifier,
    modeClick: () -> Unit,
    text: String,
    mode: PrivateKeyMode,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            style = MnemonicWalletRecoverTheme.typography.body1,
            text = text,
            color = MnemonicWalletRecoverTheme.colors.primaryLabel,
        )

        Icon(
            modifier = Modifier
                .clickable { modeClick() }
                .padding(horizontal = 5.dp, vertical = 3.dp)
                .size(16.dp),
            imageVector = when (mode) {
                PrivateKeyMode.Hidden -> Icons.Filled.Visibility
                PrivateKeyMode.Show -> Icons.Filled.VisibilityOff
            },
            contentDescription = null,
            tint = MnemonicWalletRecoverTheme.colors.primaryLabel,
        )
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
            callback = object : WalletInfoDialogCallback {
                override fun onDismiss() {}
                override fun onCopyPrivateKeyClick() {}
                override fun onPrivateKeyModeClick() {}

            },
        )
    }
}