import * as React from 'react'
import { withStyles, WithStyles, Theme } from '@material-ui/core/styles'
import Paper from '@material-ui/core/Paper'
import Typography from '@material-ui/core/Typography'
import Button from '@material-ui/core/Button'
import Dialog from '@material-ui/core/Dialog'
import DialogTitle from '@material-ui/core/DialogTitle'
import DialogContent from '@material-ui/core/DialogContent'
import DialogActions from '@material-ui/core/DialogActions'
import Snackbar from '@material-ui/core/Snackbar'
import IconButton from '@material-ui/core/IconButton'
import CloseIcon from '@material-ui/icons/Close'
import Select from '@material-ui/core/Select'
import FormControl from '@material-ui/core/FormControl'
import InputLabel from '@material-ui/core/InputLabel'
import Input from '@material-ui/core/Input'

import { simpleData, large, metaData, errorEmptyMetaData, errorTypeMetaData, errorStructureMetaData } from './data'

import convertFileToCsv from './utils/convertFileToCsv'

const styles = (theme: Theme) => ({
    title: {
        color: theme.palette.primary.main,
        textAlign: 'center' as 'center',
        margin: '2rem',
        marginTop: '4.5rem',
    },
    root: {
        margin: '2rem',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        flexDirection: 'column' as 'column',
    },
    messageContainer: {
        marginTop: '5rem',
        textAlign: 'center' as 'center',
    },
    message: {
        color: theme.palette.text.hint,
        fontSize: '14px',
    },
    errorMessage: {
        color: theme.palette.error.main,
    },
    close: {
        padding: theme.spacing.unit / 2,
    },
    formControl: {
        margin: theme.spacing.unit,
        minWidth: 120,
    },
})

export interface Props extends WithStyles<typeof styles> {
}

export interface State {
    dialogOpen: boolean
    snackbarOpen: boolean
    errorMessage: string
    CsvExportExample: string
}

export class Converter extends React.Component<Props, State> {
    public constructor(props: Props) {
        super(props)

        this.state = {
            dialogOpen: false,
            snackbarOpen: false,
            errorMessage: '',
            CsvExportExample: '',
        }
    }

    private handleDialogClose = () => this.setState({ dialogOpen: false })
    private handleSnackBarClose = () => this.setState({ snackbarOpen: false })
    private handleSelectChange = (evt: React.ChangeEvent<HTMLSelectElement>) => {
        evt.persist()
        this.setState({
            CsvExportExample: evt.target.value
        }, () => {
            if (evt.target && evt.target.value) {
                this.exportCSV(evt.target.value)
            }
        })
    }

    private getFileData(CsvExportExample: string) {
        switch (CsvExportExample) {
            case 'sampleData':
                return {
                    metaData,
                    fileData: simpleData,
                }

            case 'errorEmptyMetaData':
                return {
                    metaData: errorEmptyMetaData,
                    fileData: simpleData,
                }

            case 'errorTypeMetaData':
                return {
                    metaData: errorTypeMetaData,
                    fileData: simpleData,
                }

            case 'errorStructureMetaData':
                return {
                    metaData: errorStructureMetaData,
                    fileData: simpleData,
                }

            case 'largeData':
                return {
                    metaData,
                    fileData: large,
                }

            default:
                return {
                    metaData,
                    fileData: simpleData,
                }
        }
    }

    private exportCSV = async (CsvExportExample: string) => {
        try {
            const fileData = this.getFileData(CsvExportExample)

            await convertFileToCsv(fileData.metaData, fileData.fileData)

            this.setState({ snackbarOpen: true })
        } catch (error) {
            this.setState({
                dialogOpen: true,
                errorMessage: error && error.message,
            })
        }
    }

    render() {
        const { classes } = this.props
        const { dialogOpen, errorMessage, snackbarOpen, CsvExportExample } = this.state

        return (
            <div>
                <Typography variant='h4' className={classes.title}>
                    File Format Converter
                </Typography>
                <Paper
                    className={classes.root} elevation={1}
                >
                    <FormControl className={classes.formControl}>
                        <InputLabel htmlFor="age-native-simple">Select file to export</InputLabel>
                        <Select
                            native
                            value={CsvExportExample}
                            onChange={this.handleSelectChange}
                            input={<Input />}
                        >
                            <option value="" />
                            <option value='sampleData'>Readme Sample File</option>
                            <option value='largeData'>Large File</option>
                            <option value='errorEmptyMetaData'>Empty MetaData (Error Example)</option>
                            <option value='errorTypeMetaData'>Wront Type MetaData (Error Example)</option>
                            <option value='errorStructureMetaData'>Wront Structure MetaData (Error Example)</option>
                        </Select>
                    </FormControl>
                    <div className={classes.messageContainer}>
                        <Typography variant='body2' className={classes.message}>
                            This is a tool to convert a fixed file into csv, and download it to local.
                        </Typography>

                        <Typography variant='body2' className={classes.message}>
                            Error will be shown if error example has been selected.
                        </Typography>
                    </div>
                </Paper>
                <Dialog
                    open={dialogOpen}
                    onClose={this.handleDialogClose}
                >
                    <DialogTitle id="alert-dialog-title">{'Error'}</DialogTitle>
                    <DialogContent>
                        <Typography variant='body2' className={classes.errorMessage}>
                            {errorMessage}
                        </Typography>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleDialogClose} color="primary">
                            OK
                        </Button>
                    </DialogActions>
                </Dialog>
                <Snackbar
                    anchorOrigin={{
                        vertical: 'top',
                        horizontal: 'center',
                    }}
                    open={snackbarOpen}
                    autoHideDuration={2000}
                    onClose={this.handleSnackBarClose}
                    message={<span id='message-id'>CSV file exported successfully!</span>}
                    action={[
                        <IconButton
                            key="close"
                            color='inherit'
                            className={classes.close}
                            onClick={this.handleSnackBarClose}
                        >
                            <CloseIcon />
                        </IconButton>,
                    ]}
                />
            </div>
        )
    }
}

export default withStyles(styles)(Converter)
