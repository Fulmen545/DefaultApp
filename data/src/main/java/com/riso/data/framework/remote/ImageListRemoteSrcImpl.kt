package com.riso.data.framework.remote

import com.riso.data.networking.NetworkCallFactory
import com.riso.core.dispatcher.AppDispatchers
import com.riso.data.framework.api.RemoteApi
import com.riso.data.framework.api.entity.ImageData
import com.riso.data.networking.BaseRemoteSrc
import com.riso.data.repository.remote.ImageListRemoteSrc
import com.riso.core.Result

class ImageListRemoteSrcImpl(
    private val remoteApi: RemoteApi,
    networkCallFactory: NetworkCallFactory,
    appDispatchers: AppDispatchers
): BaseRemoteSrc(networkCallFactory, appDispatchers), ImageListRemoteSrc {

    override suspend fun loadImages(): Result<List<ImageData>> =  networkCall {
        remoteApi.getImageList()
    }

}