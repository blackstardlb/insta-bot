import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {BehaviorSubject, EMPTY, Observable, Subscription} from 'rxjs';
import {DiscordServerService} from '../../service/discord-server.service';
import {catchError, flatMap, map, tap} from 'rxjs/operators';
import {BaseDiscordServer, FullDiscordServer} from '../../model/base-discord-server';
import {environment} from '../../environments/environment';
import {MatSnackBar} from '@angular/material';
import {AbstractControl, FormControl, FormGroup, ValidationErrors} from '@angular/forms';

@Component({
    selector: 'app-manageable-server',
    templateUrl: './manageable-server.component.html',
    styleUrls: ['./manageable-server.component.scss']
})
export class ManageableServerComponent implements OnInit, OnDestroy {

    constructor(private route: ActivatedRoute, private discordServerService: DiscordServerService, private snackBar: MatSnackBar) {
    }

    isLoading: BehaviorSubject<FullDiscordServer>;
    error: BehaviorSubject<string>;
    form: FormGroup;
    private selectedChannel: FormControl;
    private serverId: Observable<string>;
    private server: Observable<FullDiscordServer>;
    private subscriptions: Subscription[] = [];

    validate(control: AbstractControl): ValidationErrors | null {
        if ((control.parent && control.parent.get('server').value.canTalk) || (control as any).changed) {
            return null;
        }

        control.markAsDirty();
        control.markAsTouched();
        return {cantTalk: 'cantTalkHere'};
    }

    ngOnInit() {
        this.isLoading = new BehaviorSubject<FullDiscordServer>(undefined);
        this.error = new BehaviorSubject<string>('none');
        this.serverId = this.route.paramMap.pipe(map(paramMap => paramMap.get('id')));
        this.server = this.serverId
            .pipe(
                tap(() => this.isLoading.next(undefined)),
                tap(() => this.error.next('none')),
                flatMap(id => this.discordServerService.getServer(id)
                    .pipe(
                        tap(server => {
                            this.initializeControl(server);
                            this.selectedChannel.setValue(server.selectedChannelId, {emitEvent: false});
                        }),
                        catchError(err => {
                            console.log(err);
                            this.error.next(err);
                            return EMPTY;
                        })
                    )
                )
            );
        this.subscriptions.push(this.server.subscribe(this.isLoading));
    }

    initializeControl(server: FullDiscordServer) {
        this.selectedChannel = new FormControl(server.selectedChannelId, [this.validate]);
        const sub = this.selectedChannel.valueChanges.subscribe(value => {
            if ((this.selectedChannel as any).changed) {
                return;
            }
            (this.selectedChannel as any).changed = true;
            this.selectedChannel.setValue(this.selectedChannel.value);
        });
        this.subscriptions.push(sub);
        this.form = new FormGroup({
            server: new FormControl(server),
            isBotEnabled: new FormControl(server.botEnabled),
            instagramName: new FormControl(server.instagramName),
            selectedChannelId: this.selectedChannel,
        });
    }

    addBotURL(baseDiscordServer: BaseDiscordServer) {
        return environment.discordBotURL.replace('GUILD_ID', baseDiscordServer.id);
    }

    save(aServer: FullDiscordServer) {
        this.isLoading.next(undefined);
        aServer.instagramName = this.form.get('instagramName').value;
        aServer.botEnabled = this.form.get('isBotEnabled').value;
        this.subscriptions.push(this.discordServerService.updateServer(aServer).subscribe(server => {
            this.initializeControl(server);
            this.isLoading.next(server);
            this.toast('Saved');
        }, error => {
            console.log(error);
            this.isLoading.next(aServer);
            this.toast('Something went wrong while saving');
        }));
    }

    reset() {
        this.ngOnInit();
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(s => s.unsubscribe());
    }

    toast(message: string) {
        this.snackBar.open(message, null, {duration: 2000});
    }
}
